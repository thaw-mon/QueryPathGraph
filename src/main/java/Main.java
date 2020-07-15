import com.database.tool.Service.DatabaseGenerator;
import com.database.tool.Service.Schemata.ColumnFamilyModel.ColumnFamilyScalarProperty;
import com.database.tool.Service.Schemata.DocumentModel.DocumentScalarPropertyByRef;
import com.database.tool.Service.Schemata.KeyValueModel.KeyValueProperty;

import java.util.List;

/**
 * The entry function
 */
public class Main {

    //1.Read input parameters
    static class Arg {
        private String path;
        private String file;
        private int type;

        public Arg(String[] args) {
//            assert args.length == 6;
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-path")) {
                    path = args[i + 1];
                }
                if (args[i].equals("-file")) {
                    file = args[i + 1];
                }
                if (args[i].equals("-type")) {
                    switch (args[i + 1]) {
                        case "KeyValue":
                            type = 0;
                            break;
                        case "ColumnFamily":
                            type = 1;
                            break;
                        case "Document":
                            type = 2;
                            break;
                        default:
//                            assert false;
                            break;
                    }
                }
            }
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
    //Java main -path  xx -file xx.info -type (0 1 2)
    //java -jar NoSQL-DB-Generator.jar -path resources/experiments -file EAC.info -type ColumnFamily
    public static void main(String[] args) {
        Arg argument = new Arg(args);
        DatabaseGenerator databaseGenerator;

        databaseGenerator = new DatabaseGenerator(argument.getPath(), argument.getFile());
        databaseGenerator.createSchemata(argument.getType());
        System.out.println("Schemata : ");
        switch (argument.type) {
            case 0:
                List<KeyValueProperty> keyValuePropertyList = databaseGenerator.keyValuePropertyList;
                for (KeyValueProperty keyValueProperty : keyValuePropertyList) {
                    System.out.println(keyValueProperty);
                }
                break;
            case 1:
                List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList = databaseGenerator.columnFamilyScalarPropertyList;
                for (ColumnFamilyScalarProperty columnFamilyScalarProperty : columnFamilyScalarPropertyList) {
                    System.out.println(columnFamilyScalarProperty);
                }
                break;
            case 2:
                List<DocumentScalarPropertyByRef> documentScalarPropertyList = databaseGenerator.documentScalarPropertyList;
                for (DocumentScalarPropertyByRef documentScalarProperty : documentScalarPropertyList) {
                    System.out.println(documentScalarProperty);
                }
                break;
        }

    }
}
