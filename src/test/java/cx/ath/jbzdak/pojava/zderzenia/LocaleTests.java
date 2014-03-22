package cx.ath.jbzdak.pojava.zderzenia;

import cx.ath.jbzdak.pojava.zderzenia.translation.LocaleHolder;
import org.junit.Test;

import java.util.Locale;

public class LocaleTests{


    @Test
    public void checkHasPolishLocale(){
        LocaleHolder.getHolder().setLocale(new Locale("pl"));
    }


    @Test
    public void checkHasEnglishLocale(){
        LocaleHolder.getHolder().setLocale(new Locale("en"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void checkRaisesOnOtherLocale(){

        LocaleHolder.getHolder().setLocale(new Locale("ru"));
    }

}
