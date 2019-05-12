package com.fasteam.system;

import com.fasteam.system.gif.AnimatedGifEncoder;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 验证码
 */
public class CaptchaFactory extends ListImageCaptchaEngine {
    /**
     * 保存于session 中的key
     */
    private static final String KEY_SESSION_VALUE = "CAPTCHA$OBJ";

    //    private static final String APP_CAPTCHA_CHARS = "app.captcha.chars";
//    private static final String APP_CAPTCHA_WIDTH = "app.captcha.width";
//    private static final String APP_CAPTCHA_HEIGHT = "app.captcha.height";
//    private static final String APP_CAPTCHA_MIN_WORDS = "app.captcha.min.words";
//    private static final String APP_CAPTCHA_MAX_WORDS = "app.captcha.max.words";
//    private static final String APP_CAPTCHA_MIN_FONT_SIZE = "app.captcha.min.font.size";
//    private static final String APP_CAPTCHA_MAX_FONT_SIZE = "app.captcha.max.font.size";
//    private static final String DEFAULT_CAPTCHA_CHARS = "123456789abcdefghijlmnopkrstuvxzyk";
    private static final String APP_CAPTCHA_CHARS = "1234567890ABCDEFG";
    private static final int APP_CAPTCHA_WIDTH = 75;
    private static final int APP_CAPTCHA_HEIGHT = 30;
    private static final int APP_CAPTCHA_MIN_WORDS = 4;
    private static final int APP_CAPTCHA_MAX_WORDS = 4;
    private static final int APP_CAPTCHA_MIN_FONT_SIZE = 18;
    private static final int APP_CAPTCHA_MAX_FONT_SIZE = 18;
    private static final String DEFAULT_CAPTCHA_CHARS = "123456789abcdefghijlmnopkrstuvxzyk";
    private static final Logger LOG = LoggerFactory.getLogger(CaptchaFactory.class);

    private static CaptchaFactory classInstance = new CaptchaFactory();

    private static String charsInUse;

    public CaptchaFactory() {
        //init test gen one
        ImageCaptcha nextImageCaptcha = getNextImageCaptcha();
        if (nextImageCaptcha != null) {
            nextImageCaptcha.disposeChallenge();
        }
    }

//    private static int getIntValue(String key) {
//        return getIntValue(key, -1);
//    }

//    private static int getIntValue(String key, int def) {
//        try {
//            return Integer.parseInt(AppConfig.getInstance().getProperty(key, String.valueOf(def)));
//        } catch (Throwable e) {
//            return def;
//        }
//    }

    private void initializeChars() {
        charsInUse = APP_CAPTCHA_CHARS.toUpperCase();
    }

    /**
     * Gets the singleton
     *
     * @return Instance of Captcha class
     */
    public static CaptchaFactory getInstance() {
        return classInstance;
    }

    protected void buildInitialFactories() {
        this.initializeChars();

        List<BackgroundGenerator> backgroundGeneratorList = new ArrayList<BackgroundGenerator>();
        List<RandomTextPaster> textPasterList = new ArrayList<RandomTextPaster>();
        List<FontGenerator> fontGeneratorList = new ArrayList<FontGenerator>();

        int width = APP_CAPTCHA_WIDTH;
        int height = APP_CAPTCHA_HEIGHT;
        int minWords = APP_CAPTCHA_MIN_WORDS;
        int maxWords = APP_CAPTCHA_MAX_WORDS;
        final int minFontSize = APP_CAPTCHA_MIN_FONT_SIZE;
        final int maxFontSize = APP_CAPTCHA_MAX_FONT_SIZE;

//        this.backgroundGeneratorList.add(new GradientBackgroundGenerator(new Integer(width),
//                new Integer(height), Color.blue, Color.LIGHT_GRAY));
//        this.backgroundGeneratorList.add(new GradientBackgroundGenerator(new Integer(width),
//                new Integer(height), Color.WHITE, Color.RED));
        backgroundGeneratorList.add(new GradientBackgroundGenerator(width,
                height, Color.LIGHT_GRAY, Color.WHITE));
        backgroundGeneratorList.add(new GradientBackgroundGenerator(width,
                height, Color.CYAN, Color.LIGHT_GRAY));
        backgroundGeneratorList.add(new GradientBackgroundGenerator(width,
                height, Color.CYAN, Color.getHSBColor(230, 230, 230)));
        backgroundGeneratorList.add(new FunkyBackgroundGenerator(width, height));
        //this.backgroundGeneratorList.add(new FunkyBackgroundGenerator(new Integer(250), new Integer(50)));

//        this.textPasterList.add(new RandomTextPaster(new Integer(minWords), new Integer(maxWords), Color.getHSBColor(253,253,222)));
        textPasterList.add(new RandomTextPaster(minWords, maxWords, Color.BLUE));
        textPasterList.add(new RandomTextPaster(minWords, maxWords, Color.DARK_GRAY));
//        this.textPasterList.add(new RandomTextPaster(new Integer(minWords), new Integer(maxWords), Color.MAGENTA));
        textPasterList.add(new RandomTextPaster(minWords, maxWords, Color.BLACK));
//        this.textPasterList.add(new RandomTextPaster(new Integer(minWords), new Integer(maxWords), Color.ORANGE));

//        fontGeneratorList.add(new TwistedAndShearedRandomFontGenerator(minFontSize, maxFontSize));

//        // 字体格式
        Font[] fontsList = new Font[]{new Font("Arial", 0, minFontSize),
                new Font("Tahoma", 0, minFontSize), new Font("Verdana", 0, minFontSize)};
        // 文字的大小
        FontGenerator fontGenerator = new RandomFontGenerator(minFontSize, maxFontSize, fontsList);
        fontGeneratorList.add(fontGenerator);
        // Create a random word generator
        WordGenerator words = new RandomWordGenerator(charsInUse);


        for (FontGenerator fontGeny : fontGeneratorList) {
            for (BackgroundGenerator bkgdGeny : backgroundGeneratorList) {
                for (RandomTextPaster textPaster : textPasterList) {
                    WordToImage word2image = new ComposedWordToImage(fontGeny, bkgdGeny, textPaster);
                    // Creates a ImageCaptcha Factory
                    ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
                    // Add a factory to the gimpy list (A Gimpy is a ImagCaptcha)
                    addFactory(factory);
                }
            }
        }
    }

    /**
     * create a new image captcha
     */
    public ImageCaptcha createNewCaptcha(HttpServletRequest request) {
        return createNewCaptcha(request, true);
    }

    public ImageCaptcha createNewCaptcha(HttpServletRequest request, boolean tryAgain) {
        try {
            ImageCaptcha ic = getNextImageCaptcha();
            storeImageCaptcha(request, ic);
            return ic;
        } catch (Throwable e) {
            if (tryAgain) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    LOG.error("CaptchaFactory createNewCaptcha error!", e);
                }
                return createNewCaptcha(request, false);
            } else {
                LOG.warn("Create captcha exception ,please config it:", e);
            }
        }
        return null;
    }

    public void storeImageCaptcha(HttpServletRequest request, ImageCaptcha ic) {
        request.getSession().setAttribute(KEY_SESSION_VALUE, ic);
    }

    public ImageCaptcha getImageCaptcha(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (ImageCaptcha) session.getAttribute(KEY_SESSION_VALUE);
    }

    public void removeImageCaptcha(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(KEY_SESSION_VALUE);
        }
    }

    /**
     * Get the captcha image to challenge the user
     *
     * @return BufferedImage the captcha image to challenge the user
     */
    public BufferedImage getCaptchaImage(ImageCaptcha imageCaptcha) {
        if (imageCaptcha == null) {
            return null;
        }
        return (BufferedImage) imageCaptcha.getChallenge();
    }


    public void writeCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = getCaptchaImage(createNewCaptcha(request));

        if (image == null) {
            return;
        }

        OutputStream outputStream = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Date", Calendar.getInstance().getTime().toString());
            response.setDateHeader("Expires", 0);
            String accept = request.getHeader("Accept");
            if (accept.contains("image/jpeg")) {
                LOG.debug("out write: image/jpg");
                response.setContentType("image/jpg");
                outputStream = response.getOutputStream();
                ImageIO.write(image, "jpg", outputStream);
            } else if (accept.contains("image/png")) {
                LOG.debug("out write: image/png");
                response.setContentType("image/png");
                outputStream = response.getOutputStream();
                ImageIO.write(image, "png", outputStream);
            } else {
                response.setContentType("image/gif");
                AnimatedGifEncoder ge = new AnimatedGifEncoder();
                ge.start(response.getOutputStream());
                ge.setSize(APP_CAPTCHA_WIDTH, APP_CAPTCHA_HEIGHT);
                ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream1);//dep
//                encoder.encode(image);
                ImageIO.write(image, "jpeg", outputStream1);
                ge.addFrame(ImageIO.read(new ByteArrayInputStream(outputStream1.toByteArray())));
                ge.finish();
            }
        } catch (Throwable ex) {
            LOG.error(ex.getMessage(), ex);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public boolean validate(HttpServletRequest request, String code) {
        try {
            ImageCaptcha captcha = getImageCaptcha(request);
            return captcha == null ? false : captcha.validateResponse(code.toUpperCase());
        } catch (Throwable e) {
            LOG.warn(e.getMessage(), e);
        } finally {
            //验证完就失效,只有效一次
            removeImageCaptcha(request);
        }
        return false;
    }
}
