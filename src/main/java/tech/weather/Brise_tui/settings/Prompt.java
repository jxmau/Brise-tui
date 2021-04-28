package tech.weather.Brise_tui.settings;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Service;

@Service
public class Prompt implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(" Brise > ");
    }
}
