/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author sankalp
 */
public enum ErrorCode {
    
    PROJECTDESERIALIZE_GENERALERROR(-1000100, ErrorMsgsConfig.PROJECTDESERIALIZE_GENERALERROR),
    PROJECTDESERIALIZE_MALFORMEDPROJECTLINE(-100101,ErrorMsgsConfig.PROJECTDESERIALIZE_MALFORMEDPROJECTLINE),
    PROJECTDESERIALIZE_NOVALUEFORKEY(-1000102,ErrorMsgsConfig.PROJECTDESERIALIZE_NOVALUEFORKEY),
    ERROR_CORRUPTEDFILE(-1000110,ErrorMsgsConfig.ERROR_CORRUPTEDFILE),
    PROJECTSERIALIZE_GENERALERROR(-1000200,ErrorMsgsConfig.PROJECTSERIALIZE_GENERALERROR),
    PROJECTSERIALIZE_REGULARGRAPH(-1000201,ErrorMsgsConfig.PROJECTSERIALIZE_REGULARGRAPH),
    PROJECTSERIALIZE_TEXTUALGRAPH(-1000202,ErrorMsgsConfig.PROJECTSERIALIZE_TEXTUALGRAPH),
    PROJECTSERIALIZE_WRITINGPROJECT(-1000203,ErrorMsgsConfig.PROJECTSERIALIZE_WRITINGPROJECT),
    ERROR_GRAPHLOAD(-201,ErrorMsgsConfig.ERROR_GRAPHLOAD),
    ERROR_SAVECOMMUNITY(-101,ErrorMsgsConfig.ERROR_SAVECOMMUNITY);

    private final int id;
    private final String msg;

    ErrorCode(int id, String msg) {
      this.id = id;
      this.msg = msg;
    }

    public int getId() {
      return this.id;
    }

    public String getMsg() {
      return this.msg;
    }

}
