package org.yu55.yagga.handler.git.command.grep;

import org.apache.commons.exec.CommandLine;
import org.yu55.yagga.handler.git.command.common.GitCommand;

public class GitGrepCommand implements GitCommand {

    private final String grepText;

    public GitGrepCommand(String grepText) {
        this.grepText = grepText;
    }

    private String escapeSearchPhraseArgument(String wanted) {
        if (wanted.startsWith("-") || wanted.startsWith(" ")) {
            return "\\" + wanted;
        } else {
            return wanted;
        }
    }

    @Override
    public CommandLine getCommandLine() {
        CommandLine commandLine = CommandLine.parse(COMMAND);
        commandLine.addArgument("grep", false);
        commandLine.addArgument("-n", false);
        commandLine.addArgument(escapeSearchPhraseArgument(grepText), false);
        return commandLine;
    }
}