package com.cabletech.commons.generatorID;

import com.cabletech.commons.generatorID.impl.*;

public class GeneratorFactory{
    public static GeneratorID createGenerator(){
        return new OracleIDImpl();
    }
}
