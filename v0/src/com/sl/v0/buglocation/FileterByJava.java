package com.sl.v0.buglocation;

import java.io.File;
import java.io.FilenameFilter;

public class FileterByJava implements FilenameFilter {
    
    private String SufixName;/*传入过滤的名称*/
    public FileterByJava(String SufixName){
        this.SufixName = SufixName;
    }

    @Override
    public boolean accept(File dir, String name) {
        //System.out.println("dir:"+dir+"------"+"name:"+name);
        return name.endsWith(SufixName);
    }

}
