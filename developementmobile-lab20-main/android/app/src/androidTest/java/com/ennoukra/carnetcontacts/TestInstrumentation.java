package com.ennoukra.carnetcontacts;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestInstrumentation {
    @Test
    public void verifieNomPackage() {
        Context contexte = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ennoukra.carnetcontacts", contexte.getPackageName());
    }
}
