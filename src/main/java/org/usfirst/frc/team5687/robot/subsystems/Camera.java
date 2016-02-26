package org.usfirst.frc.team5687.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.StreamCamera;
import org.usfirst.frc.team5687.robot.utils.CustomCameraServer;

/**
 * Created by Ben Bernard on 2/25/2016.
 */
public class Camera extends Subsystem {

    private static final int kSize640x480 = 0;
    private static final int kSize320x240 = 1;
    private static final int kSize160x120 = 2;

    CameraServer cameraServer;
    USBCamera hornsCamera;
    USBCamera intakeCamera;
    USBCamera activeCamera;
    String cameraName = RobotMap.Cameras.hornsEnd;

    Image frame;
    int m_size = 0;
    int m_fps = 15;

    private int trackingX = 0;
    private int trackingY = 0;
    private boolean track = false;

    public Camera() {
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

    }

    public synchronized void setTrackingX(int x,  int y) {
        this.trackingX = x;
        this.trackingY = y;
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StreamCamera());
    }

    public void execute() {
        DriverStation.reportError("exec", false);
        initialize();
        getImage();
        alterImage();
        cameraServer.setImage(frame);
    }

    public void getImage() {
        m_size = 1; //cameraServer.getSize();
        m_fps = 25; //cameraServer.getFPS();

        switch(cameraName) {
            case RobotMap.Cameras.hornsEnd:
                hornsCamera.getImage(frame);
            case RobotMap.Cameras.intakeEnd:
                intakeCamera.getImage(frame);
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


        NIVision.Rect targetRect = new NIVision.Rect(cY - (targetHeight / 2), cX - (targetWidth / 2), targetWidth, targetHeight);

        // Draw the target circle...
        NIVision.imaqDrawShapeOnImage(frame,frame, targetRect, NIVision.DrawMode.DRAW_VALUE, NIVision.ShapeMode.SHAPE_OVAL, 255);

        int tX;
        int tY;

        synchronized(this) {
            trackingX = 100;
            trackingY = 100;
            tX = cX + trackingX;
            tY = cY + trackingY;
        }

        NIVision.Rect trackingRect = new NIVision.Rect(tY, tX, trackingSize, trackingSize);

        DriverStation.reportError("Drawing oval at " + tX + "," + tY + " width " + trackingSize + " height " + trackingSize, false);
        // Draw the tracking circle...
        // NIVision.imaqDrawShapeOnImage(frame, frame, trackingRect, NIVision.DrawMode.DRAW_VALUE, NIVision.ShapeMode.SHAPE_OVAL, 0x00FF00);
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
        DriverStation.reportError("Camera now streaming: "+ cameraName, false);
    }

    private void stopCapture() {
        if (activeCamera!=null) {
            // activeCamera.stopCapture();
        }
    }

    private void startCapture() {
        if (activeCamera!=null) {
            DriverStation.reportError("Starting capture: "+ cameraName, false);
            activeCamera.startCapture();
        }
    }

    public void initialize() {
        if (cameraServer==null) {
            DriverStation.reportError("Initializing cameraserver!", false);
            hornsCamera = new USBCamera(RobotMap.Cameras.hornsEnd);
            intakeCamera = new USBCamera(RobotMap.Cameras.intakeEnd);
            cameraServer = CameraServer.getInstance();
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
