//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
//import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
//
//import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
//import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
//import com.qualcomm.robotcore.hardware.configuration.I2cSensor;
//
//@I2cSensor(name = "Adafruit neodriver", description = "i finally got it to work", xmlTag = "NeoDriver")
//public class HELPMEEEE extends I2cDeviceSynchDevice<I2cDeviceSynch>{
//
//    protected HELPMEEEE(I2cDeviceSynch i2cDeviceSynch, boolean deviceClientIsOwned) {
//        super(i2cDeviceSynch, deviceClientIsOwned);
//    }
//
//    protected HELPMEEEE(I2cDeviceSynch deviceClient)
//    {
//        super(deviceClient, true);
//
////        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);
//
//        super.registerArmingStateCallback(false);
//        this.deviceClient.engage();
//
//    }
//
//    @Override
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
//            return "Adafruit NeoDriver";
//        }
//
//
//
//
//
//}
