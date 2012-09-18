package com.cabletech.commons.base;

import com.cabletech.commons.generatorID.*;

public class BaseBisinessObject{
    public BaseBisinessObject(){
    }


    /**
     * ²úÉúÖ÷Key
     */
    private GeneratorID generatorID;
    public GeneratorID getGeneratorID(){
        if( generatorID != null ){
            generatorID = GeneratorFactory.createGenerator();
        }
        return generatorID;
    }

}
