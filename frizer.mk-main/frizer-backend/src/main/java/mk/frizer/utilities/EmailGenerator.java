package mk.frizer.utilities;

import mk.frizer.domain.Appointment;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
@Component
public class EmailGenerator {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

    public String createMessage( Appointment appointment) {
        return "<html>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd;'>" +
                "<h2 style='color: #333;'>Почитуван/а " + appointment.getCustomer().getFullName() + ",</h2>" +
                "<p>Направивте успешна резервација на третман преку апликацијата <strong>frizer.mk</strong>.</p>" +
                "<h3>Детали за резервацијата:</h3>" +
                "<table style='width: 100%; border-collapse: collapse; margin-bottom: 20px;'>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Почеток на третман:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getDateFrom().format(formatter) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Крај на третман:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getDateTo().format(formatter) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Салон:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getSalon().getName() + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Вработен:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getEmployee().getFullName() + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Третман:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getTreatment().getName() + "</td>" +
                "</tr>" +
                "</table>" +
                "<p style='color: #333;'>Ви благодариме на довербата!</p>" +
                "<p style='color: #333;'>Со почит,<br><p><strong>Frizer mk</strong></p>" +
                "</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
    public String createCancellationMessage( Appointment appointment) {
        return "<html>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd;'>" +
                "<h2 style='color: #333;'>Почитуван/а " + appointment.getCustomer().getFullName() + ",</h2>" +
                "<p>Успешно ја откажавте вашата резервација на третман преку апликацијата <strong>frizer.mk</strong>.</p>" +
                "<h3>Детали за резервацијата:</h3>" +
                "<table style='width: 100%; border-collapse: collapse; margin-bottom: 20px;'>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Почеток на третман:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getDateFrom().format(formatter) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Крај на третман:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getDateTo().format(formatter) + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Салон:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getSalon().getName() + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Вработен:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getEmployee().getFullName() + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>Третман:</td>" +
                "<td style='padding: 8px; border: 1px solid #ddd;'>" + appointment.getTreatment().getName() + "</td>" +
                "</tr>" +
                "</table>" +
                "<p style='color: #333;'>Со почит," +
                "<br>" +
                "<p><strong>Frizer mk</strong></p>" +
                "</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
