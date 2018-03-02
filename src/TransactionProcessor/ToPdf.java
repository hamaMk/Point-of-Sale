package TransactionProcessor;

import PointOfSale.ShoppingBasket;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import transactions.Transaction;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class ToPdf {




    static String [] data = {"this","that","Juice"};

    static Transaction transaction;

//    static ArrayList<ShoppingBasket> data1 = transaction.getItems();

    static PdfPTable salesTable = new PdfPTable(3);


    public static void print(){


        for (int i = 0; i<=5; i++){

            salesTable.addCell("item "+i);

        }
    }



    public static void printItems(ArrayList<ShoppingBasket> data){

      /*  data = transaction.getItems();

        for (ShoppingBasket x: data){
            salesTable.addCell(x.getItem());
            salesTable.addCell(String.valueOf(x.getQty()));
            salesTable.addCell(String.valueOf(x.getPrice()));
        }*/

      for(ShoppingBasket x: data){
          salesTable.addCell(x.getItem());
          salesTable.addCell(String.valueOf(x.getQty()));
          salesTable.addCell(String.valueOf(x.getPrice()));
      }



     /* for (String x: data){
          salesTable.addCell(x);

      }*/
    }



    static double total = 0;

   // static File path  = new File ("Transactions.txt");



    public  ToPdf(Transaction transaction){

        this.transaction = transaction;

    }

    public static void saveToTextFile(ObservableList<Transaction> transactions, File tmp)throws Exception{


        String fileName =  getTrueDate(new Date().toString()) +".pdf" ;


        Document doc = new Document();
        PdfWriter.getInstance(doc,new FileOutputStream(tmp));
        doc.open();
        doc.add(new Paragraph("SALES REPORT", FontFactory.getFont(FontFactory.TIMES_BOLD,18, Font.BOLD, Color.RED)));

        String date =  getTrueDate(new Date().toString());
        doc.add(new Paragraph(date));

        doc.add(new Paragraph("_____________________________________________________________________________\n"));

        doc.add(new Paragraph("                                                                          "));

        PdfPCell title = new PdfPCell(new Paragraph("title",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD,Color.RED)));
        title.setColspan(6);
        title.setHorizontalAlignment(Element.ALIGN_CENTER);
        title.setBackgroundColor(Color.BLACK);

        PdfPCell cellItem = new PdfPCell(new Paragraph("ITEM"));
        PdfPCell cellQty = new PdfPCell(new Paragraph("QTY"));
        PdfPCell cellPrice = new PdfPCell(new Paragraph("PRICE"));


        salesTable.addCell(title);
        salesTable.addCell(cellItem);
        salesTable.addCell(cellQty);
        salesTable.addCell(cellPrice);

      //  print();
        for(Transaction x: transactions ){


            printItems(x.getItems());
        }
       // printItems(transaction.getItems());

        doc.add(salesTable);
        doc.close();



        //transactions.removeAll(transactions);

        for(Transaction x: transactions ){

         /* printItems(printWriter, x.getItems());*/

          total += x.getTotalCost();
        }

    }


    public static  String getTrueDate(String Date){

        String [] temp = Date.split(" ");

        String buffet = temp[0] + " " + temp[1] + " " + temp[2];

        return buffet;
    }




}
