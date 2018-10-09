package com.bozy.cloud.enums;

/**
 * Description:
 * Created by tym on 2018/08/23 14:18.
 */
public enum UserSexEnum {
    MAN(0), WOMAN(1);

   private int value;
   UserSexEnum(int value){
      this.value = value;
   }

    public int getValue() {
        return value;
    }
}
