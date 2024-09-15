package com.example.application1;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcActivity extends AppCompatActivity {

    Connection con = null;
    String sql;
    Statement stmt;

    PreparedStatement pstm;
    ResultSet rs;
    int i,count;

    TextView[][] textviewRow = new TextView[5][3];
    Button button_insert, button_update, button_delete, button_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jdbc);

        button_insert = findViewById(R.id.button_insert);
        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);
        button_select = findViewById(R.id.button_select);

        for (i = 0; i < 5; i++) {
            textviewRow[i][0] = findViewById(getResources().getIdentifier("cat_id_r0" + (i + 1), "id", getPackageName()));
            textviewRow[i][1] = findViewById(getResources().getIdentifier("cat_name_r0" + (i + 1), "id", getPackageName()));
            textviewRow[i][2] = findViewById(getResources().getIdentifier("p_id_r0" + (i + 1), "id", getPackageName()));
        }

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver"); //連線資料庫
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://10.10.51.205:1433/ProjectC", "sa", "1234");
            stmt = con.createStatement();//建立Statement

            button_insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sql = "insert into ProjectC.dbo.Category (catId,catName,pId) values ('Test','Test','P001')";
                        stmt.execute(sql);
                        showData();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            button_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sql = "update ProjectC.dbo.Category set catName='Updated' where (pId = 'P001' AND catId = 'Test')";
                        stmt.execute(sql);
                        showData();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sql = "delete from ProjectC.dbo.Category where (pId = 'P001' AND catId = 'Test')";
                        stmt.execute(sql);
                        showData();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            button_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTextview();
                    try {
                        sql = "select TOP(5) * from ProjectC.dbo.Category";
                        rs = stmt.executeQuery(sql);
                        i = 0;
                        while (rs.next()) {
                            if (i < 5) {
                                textviewRow[i][0].setText(rs.getString("catId"));
                                textviewRow[i][1].setText(rs.getString("catName"));
                                textviewRow[i][2].setText(rs.getString("pId"));
                                i++;
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            rs.close();
            stmt.close();
            if (con != null)
                con.close();
        } catch (Exception e1) {

            e1.printStackTrace();

        }
    }

    private void clearTextview(){
        int i;
        for(i = 0; i < 5; i++){
            textviewRow[i][0].setText("");
            textviewRow[i][1].setText("");
            textviewRow[i][2].setText("");
        }
    }
    private void showData() {
        try {
            clearTextview();
            sql = "select TOP(5) * from ProjectC.dbo.Category";
            rs = stmt.executeQuery(sql);
            i = 0;
            while (rs.next()) {
                if (i < 5) {
                    textviewRow[i][0].setText(rs.getString("catId"));
                    textviewRow[i][1].setText(rs.getString("catName"));
                    textviewRow[i][2].setText(rs.getString("pId"));
                    i++;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}