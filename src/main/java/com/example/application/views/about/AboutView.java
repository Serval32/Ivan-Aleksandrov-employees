package com.example.application.views.about;
/*
import com.example.application.views.MainLayout;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.util.SharedUtil;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {
    
    
    public AboutView() {
        
        var textArea = new TextArea();
        textArea.setValue("jdsajdsajsad");
        
        //var importButton = new Button("Import from classpath", e -> readFromClasspath());
        var buffer = new MemoryBuffer();
        var upload = new Upload(buffer);
        add(upload, textArea);
         upload.addSucceededListener(e -> {
           timeWorking(buffer.getInputStream());
        });
        
        
        
        /*
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("This place intentionally left empty");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
*/
    /*}
    
    public void timeWorking throws Exception {
        var parser = new CSVParserBuilder().withSeparator(';').build();
        var reader = new CSVReaderBuilder(new InputStreamReader(inputStream)).withCSVParser(parser).build();
        List<String[]> reader.readAll() 
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
    private void timeWorking(InputStream inputStream) {
        var parser = new CSVParserBuilder().withSeparator(';').build();
        var reader = new CSVReaderBuilder(new InputStreamReader(inputStream)).withCSVParser(parser).build();
        try {
            var entries = reader.readAll();
            
            var headers = entries.get(0);
            
            List<List<String>> records = new ArrayList<List<String>>();
            
            try (
                CSVReader csvReader = new CSVReader(); 
                String[] values = null;
                while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
                }
            }
            
            /*for (int i = 0; i < headers.length; i++) {
                int colIndex = i;
                grid.addColumn(row -> row[colIndex])
                    .setHeader(SharedUtil.camelCaseToHumanFriendly(headers[colIndex]));
            }

            grid.setItems(entries.subList(1, entries.size()));
            
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

}
*/