package com.entdiy.mdm;

import com.entdiy.ddl.DdlGeneratorHibernate54;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class MdmDBScriptGenerator {

    public static void main(String[] args) {
        //create-drop,create
        DdlGeneratorHibernate54.generateDdl(SchemaExport.Action.BOTH, "com.entdiy.mdm");
    }
}
