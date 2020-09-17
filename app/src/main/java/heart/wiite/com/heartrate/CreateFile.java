package heart.wiite.com.heartrate;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateFile {

    public void generateNoteOnSD(Context context, String eventName) {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        folder.mkdirs();
        File file = new File(folder + "/VendorHeartRateApp.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                try {
                    FileOutputStream fileinput = new FileOutputStream(file, true);
                    PrintStream printstream = new PrintStream(fileinput);
                    printstream.print("");
                    fileinput.close();
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.US);
            String date = df.format(Calendar.getInstance().getTime());

            OutputStreamWriter file_writer = new OutputStreamWriter(new FileOutputStream(file, true));
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            buffered_writer.write(eventName + "\n");
            buffered_writer.write("" + date + "\n");
            buffered_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        File file = new File(folder + "/log.txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
