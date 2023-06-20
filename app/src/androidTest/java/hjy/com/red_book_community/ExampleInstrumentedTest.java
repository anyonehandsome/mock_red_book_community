package hjy.com.red_book_community;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String oldDbPath = appContext.getDatabasePath("privateTourGuide.db").getPath();

        InputStream newDbInputStream = appContext.getAssets().open("privateTourGuide.db");

        OutputStream newDbOutputStream = new FileOutputStream(oldDbPath);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = newDbInputStream.read(buffer)) > 0) {
            newDbOutputStream.write(buffer, 0, length);
        }

        newDbInputStream.close();
        newDbOutputStream.close();
    }
}
