package com.example.application.views.csv;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.util.SharedUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@PageTitle("CSV")
@Route(value = "csv", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class CSVView extends VerticalLayout {
    Grid<String[]> grid = new Grid<>();

    public CSVView() {
        var importButton = new Button("Import from classpath", e -> readFromClasspath());
        var buffer = new MemoryBuffer();
        var upload = new Upload(buffer);
        upload.addSucceededListener(e -> {
           displayCsv(buffer.getInputStream());

                   });
        add(grid,
            new HorizontalLayout(importButton, upload)
        );
    }

    private void readFromClasspath(){
        displayCsv(getClass().getClassLoader().getResourceAsStream("contacts.csv"));
    }

    private void displayCsv(InputStream resourceAsStream) {
        var parser = new CSVParserBuilder().withSeparator(',').build();
        var reader = new CSVReaderBuilder(new InputStreamReader(resourceAsStream)).withCSVParser(parser).build();

        try {
            var entries = reader.readAll();
            
            var headers = entries.get(0);

            for (int i = 0; i < headers.length; i++) {
                int colIndex = i;
                grid.addColumn(row -> row[colIndex])
                    .setHeader(SharedUtil.camelCaseToHumanFriendly(headers[colIndex]));
            }

            var label = new Label();
            label.setText("Result: " + getResult(entries));// Add the label to your Layout.

            grid.setItems(entries.subList(1, entries.size()));
            add(grid,
                    label
            );
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getResult(List<String[]> lines) throws ParseException, IOException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var data = new HashMap<String, List<EmployeeDetails>>();

        for (var line : lines) {
            String[] str = line;

            final String employeeId = str[0];
            final String projectId = str[1];

            final String dateFromString = str[2].trim();
            final String dateToString = str[3].trim();

            final Date from = dateFromString.equals("NULL") ? new Date() : formatter.parse(dateFromString);
            final Date to = dateToString.equals("NULL") ? new Date() : formatter.parse(dateToString);

            if(!data.containsKey(projectId)) {
                var details = new ArrayList<EmployeeDetails>();
                data.put(projectId, details);
            }

            var details = data.get(projectId);
            details.add(new EmployeeDetails(employeeId, getDifferenceDays(from, to)));
            data.put(projectId, details);
        }

        var iterator = data.entrySet().iterator();

        var result = new ArrayList<String>();

        while (iterator.hasNext()) {
            var entry = iterator.next();

            if(entry.getValue().isEmpty() || entry.getValue().size() == 1) {
                continue;
            }

            var list = entry.getValue();
            list.sort(Comparator.comparing(o -> o.workingDays));

            var firstEmployee = list.get(0);
            var secondEmployee = list.get(1);

            result.add(firstEmployee.id);
            result.add(secondEmployee.id);
        }

        return String.join(",", result);
    }

    public long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private static String convertInputStreamToString(InputStream is) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[100000];
        int length;
        while ((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        // Java 1.1
        //return result.toString(StandardCharsets.UTF_8.name());

        return result.toString("UTF-8");

        // Java 10
        //return result.toString(StandardCharsets.UTF_8);

    }

}

class EmployeeDetails {
    String id;
    Long workingDays;

    public EmployeeDetails(String id, Long workingDays) {
        this.id = id;
        this.workingDays = workingDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Long workingDays) {
        this.workingDays = workingDays;
    }
}

