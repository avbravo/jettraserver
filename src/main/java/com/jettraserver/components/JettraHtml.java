/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jettraserver.components;

/**
 *
 * @author avbravo
 */
public class JettraHtml{
    private String content;

    public JettraHtml() {
        this("");
    }

    public JettraHtml HtmlDiv(){
        return this;
    }
    public JettraHtml(String html) {
        this.content = html;
    }

    public String html() {
        return "<html>%s</html>"+ content;
    }

//    public JettraDocument header(String header) {
//        return new JettraDocument(format("%s <h1>%s</h1>", content, header));
//    }
//
//    public JettraDocument paragraph(String paragraph) {
//        return new JettraDocument(format("%s <p>%s</p>", content, paragraph));
//    }
//
//    public JettraDocument horizontalLine() {
//        return new JettraDocument(format("%s <hr>", content));
//    }
//
//    public JettraDocument orderedList(String... items) {
//        String listItems = stream(items).map(el -> format("<li>%s</li>", el)).collect(joining();
//        return new JettraDocument(format("%s <ol>%s</ol>", content, listItems);
//    }
//    
//    private String format(String text,String contenido, String header){
//        return text;
//    }
//    private String format(String text,String contenido){
//        return text;
//    }
    
    private String generate(String text){
        System.out.println("");
        return text;
    }
}
