
package com.example.financefx.model;

public class Expense {
    private int id;
    private String description, category, date; // yyyy-MM-dd
    private double amount;
    private int userId;

    public Expense(int id, String desc, String cat, double amount, String date, int userId){
        this.id=id; this.description=desc; this.category=cat; this.amount=amount; this.date=date; this.userId=userId;
    }
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getCategory(){return category;} public void setCategory(String c){this.category=c;}
    public double getAmount(){return amount;} public void setAmount(double a){this.amount=a;}
    public String getDate(){return date;} public void setDate(String d){this.date=d;}
    public int getUserId(){return userId;} public void setUserId(int u){this.userId=u;}
}
