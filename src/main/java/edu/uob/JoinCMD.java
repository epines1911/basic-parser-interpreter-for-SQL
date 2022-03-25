package edu.uob;

public class JoinCMD extends DBcmd{
    Table tb1;
    Table tb2;
    Attribute column1;
    Attribute column2;
    String result;
    public JoinCMD(DBController ctrl, String tbName1,
                   String tbName2, String aName1, String aName2) {
        tb1 = (Table) ctrl.getCurrentDB().tables.get(tbName1);
        tb2 = (Table) ctrl.getCurrentDB().tables.get(tbName2);
    }

    private void setAttribute(Table tb, String attributeName) {
        //
    }

    @Override
    String query() {
        return result;
    }
}
