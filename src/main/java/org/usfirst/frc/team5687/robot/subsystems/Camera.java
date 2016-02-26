package org.usfirst.frc.team5687.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.sun.webkit.ContextMenu;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.StreamCamera;
import org.usfirst.frc.team5687.robot.utils.CustomCameraServer;

import static org.usfirst.frc.team5687.robot.Robot.pitracker;

/**
 * Created by Ben Bernard on 2/25/2016.
 */
public class Camera extends Subsystem {

    private static final int kSize640x480 = 0;
    private static final int kSize320x240 = 1;
    private static final int kSize160x120 = 2;

    CustomCameraServer cameraServer;
    USBCamera hornsCamera;
    USBCamera intakeCamera;
    USBCamera activeCamera;
    String cameraName = RobotMap.Cameras.hornsEnd;

    Image frame;
    int m_size = 0;
    int m_fps = 15;

    private int trackingX = 0;
    private int trackingY = 0;
    private boolean sighted = false;
    private boolean track = true;


    public Camera() {
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

    }

    public synchronized void setTrack(boolean on) {
        track = on;
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StreamCamera());
    }

    public void execute() {
        initialize();
        getImage();
        if (track) {
            getTracking();
            alterImage();
        }
        cameraServer.setImage(frame);
    }


    public void getTracking() {
        trackingX =  0;
        trackingY = 0;
        sighted = pitracker.getBoolean("TargetSighted", false);
        double width = pitracker.getNumber("width", 0);
        double centerX = pitracker.getNumber("centerX", 0);
        double targetX = -106; // pitracker.getNumber("inputs/TARGET_CENTER_X", 0);
        double targetWidth = 148; //pitracker.getNumber("inputs/TARGET_WIDTH", 0);

        trackingX = (int)(centerX - targetX);
        trackingY = (int)(width - targetWidth) * 10;

    }

    public void getImage() {
        m_size = cameraServer.getSize();
        m_fps = cameraServer.getFPS();

        if (activeCamera!=null) {
            activeCamera.getImage(frame);
        }
    }

    public void alterImage() {

        int targetHeight = 0;
        int targetWidth = 0;
        int trackingSize = 0;

        int cY = 60;
        int cX = 80;
        switch (m_size) {
            case kSize160x120:
                cX = 80;
                cY = 60;

                targetHeight = 120 / 8;
                targetWidth = 120 / 10;
                trackingSize = 120 / 15;
                break;
            case kSize320x240:
                cX = 160;
                cY = 120;

                targetHeight = 240 / 8;
                targetWidth = 240 / 10;
                trackingSize = 240 / 15;
                break;
            case kSize640x480:
                cX = 320;
                cY = 240;

                targetHeight = 480 / 4;
                targetWidth = 640 / 5;
                trackingSize = 480 / 5;
                break;
            default:
                return;
        }

        int tX;
        int tY;
        boolean tS = false;
        synchronized(this) {
            tX = cX + trackingX;
            tY = cY + trackingY;
            tS = sighted;
        }

        int targetColor = 255;

        NIVision.Rect targetRect = new NIVision.Rect(cY - (targetHeight / 2), cX - (targetWidth / 2), targetWidth, targetHeight);

        // SHow the target as green if the tracking circle is inside it!
        if (tX>targetRect.left && tY>targetRect.top
                && tX + trackingSize < targetRect.left + targetRect.width
                && tY + trackingSize < targetRect.top + targetRect.height) {
            targetColor = 0x00FF00;
        }
        // Draw the target circle...
        NIVision.imaqDrawShapeOnImage(frame,frame, targetRect, NIVision.DrawMode.DRAW_VALUE, NIVision.ShapeMode.SHAPE_OVAL, targetColor);


        if (tS) {
            NIVision.Rect trackingRect = new NIVision.Rect(tY, tX, trackingSize, trackingSize);
            if (trackingRect.left < 0) { trackingRect.left = 0; }
            if (trackingRect.top < 0) { trackingRect.top = 0; }

            if (trackingRect.top > (cY * 2) - trackingSize) { trackingRect.top = (cY * 2) - trackingSize; }
            if (trackingRect.left > (cX * 2) - trackingSize) { trackingRect.left = (cX * 2) - trackingSize; }

            // Draw the tracking circle...
            NIVision.imaqDrawShapeOnImage(frame, frame, trackingRect, NIVision.DrawMode.DRAW_VALUE, NIVision.ShapeMode.SHAPE_OVAL, 0x00FF00);
        }
    }

    /**
     * This function will switch the cameraName currently streaming to the DriverStation
     */
    public void switchCameras(int direction) {
        stopCapture();
        if (direction==-1) {
            cameraName = RobotMap.Cameras.intakeEnd;
            activeCamera = intakeCamera;
        } else {
            cameraName = RobotMap.Cameras.hornsEnd;
            activeCamera = hornsCamera;
        }
        startCapture();
        // cameraServer.startAutomaticCapture(activeCamera);
        DriverStation.reportError("Camera now streaming: "+ cameraName, false);
    }

    private void stopCapture() {
        if (activeCamera!=null) {
            activeCamera.stopCapture();
            activeCamera = null;
        }
    }

    private void startCapture() {
        if (activeCamera!=null) {
            activeCamera.startCapture();
        }
    }

    public void initialize() {
        if (cameraServer==null) {
            DriverStation.reportError("Initializing cameraserver!", false);
            hornsCamera = new USBCamera(RobotMap.Cameras.hornsEnd);
            intakeCamera = new USBCamera(RobotMap.Cameras.intakeEnd);
            cameraServer = CustomCameraServer.getInstance();
            cameraServer.setQuality(50);
            switchCameras(1);
        }
        if (activeCamera!=null) {
            switch (m_size) {
                case kSize160x120:
                    activeCamera.setSize(160, 120);
                    break;
                case kSize320x240:
                    activeCamera.setSize(320, 240);
                    break;
                case kSize640x480:
                    activeCamera.setSize(640, 480);
                    break;
            }
            activeCamera.setFPS(m_fps);
        }
    }

    public void reset() {
        if (hornsCamera!=null) {
            hornsCamera.stopCapture();
            hornsCamera = null;
        }
        hornsCamera = new USBCamera(RobotMap.Cameras.hornsEnd);

        if (intakeCamera!=null) {
            intakeCamera.stopCapture();
            intakeCamera = null;
        }
        intakeCamera = new USBCamera(RobotMap.Cameras.intakeEnd);

        if (cameraName == RobotMap.Cameras.intakeEnd) {
            activeCamera = intakeCamera;
        } else {
            activeCamera = hornsCamera;
        }

        if (activeCamera!=null) {
            switch (m_size) {
                case kSize160x120:
                    activeCamera.setSize(160, 120);
                    break;
                case kSize320x240:
                    activeCamera.setSize(320, 240);
                    break;
                case kSize640x480:
                    activeCamera.setSize(640, 480);
                    break;
            }
            activeCamera.setFPS(m_fps);
        }
    }
}
