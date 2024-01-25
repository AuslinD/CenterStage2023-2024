//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
//import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
//
//import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
//import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
//import com.qualcomm.robotcore.hardware.configuration.I2cSensor;
//
//public class HELPMEEEE {
//
//    public class HELPMEEE extends I2cDeviceSynchDevice<I2cDeviceSynch>
//    {
//        @Override
//        public Manufacturer getManufacturer()
//        {
//
//            return Manufacturer.Adafruit;
//        }
//
//        @Override
//        protected synchronized boolean doInitialize()
//        {
//            return true;
//        }
//
//        @Override
//        public String getDeviceName()
//        {
//
//            return "Adafruit Neodriver";
//        }
//
//        public HELPMEEE(I2cDeviceSynch deviceClient)
//        {
//            super(deviceClient, true);
//            super.registerArmingStateCallback(false);
//            this.deviceClient.engage();
//
//        }
//    }
//
//    @I2cSensor(name = "Adafruit Neodriver", description = "WHYYYYYY", xmlTag = "Neodriver")
//    public class HELPMEEE extends I2cDeviceSynchDevice<I2cDeviceSynch>{
//        @Override
//        protected boolean doInitialize() {
//            return false;
//        }
//
//        @Override
//        public Manufacturer getManufacturer() {
//            return null;
//        }
//
//        @Override
//        public String getDeviceName() {
//            return null;
//        }
//    }
//}
