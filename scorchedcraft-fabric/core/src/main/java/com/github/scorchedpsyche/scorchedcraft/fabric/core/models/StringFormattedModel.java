package com.github.scorchedpsyche.scorchedcraft.fabric.core.models;

public class StringFormattedModel {
    public StringFormattedModel()
    {
        stringBuilder = new StringBuilder();
    }
    public StringFormattedModel(String message)
    {
        stringBuilder = new StringBuilder(message);
    }

    private StringBuilder stringBuilder;

    public StringFormattedModel aqua(String str)
    {
        stringBuilder.append("§b");
        add(str);

        return this;
    }
    public StringFormattedModel aquaR(String str)
    {
        stringBuilder.append("§b");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel black(String str)
    {
        stringBuilder.append("§0");
        add(str);

        return this;
    }
    public StringFormattedModel black(int str)
    {
        stringBuilder.append("§0");
        add(str);
        
        return this;
    }
    public StringFormattedModel blackR(String str)
    {
        stringBuilder.append("§0");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel blue(String str)
    {
        stringBuilder.append("§9");
        add(str);

        return this;
    }
    public StringFormattedModel blueR(String str)
    {
        stringBuilder.append("§9");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel darkAqua(String str)
    {
        stringBuilder.append("§3");
        add(str);

        return this;
    }
    public StringFormattedModel darkAquaR(String str)
    {
        stringBuilder.append("§3");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel darkBlue(String str)
    {
        stringBuilder.append("§1");
        add(str);

        return this;
    }
    public StringFormattedModel darkBlueR(String str)
    {
        stringBuilder.append("§1");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel darkGray(String str)
    {
        stringBuilder.append("§8");
        add(str);

        return this;
    }
    public StringFormattedModel darkGrayR(String str)
    {
        stringBuilder.append("§8");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel darkGreen(String str)
    {
        stringBuilder.append("§2");
        add(str);

        return this;
    }
    public StringFormattedModel darkGreenR(String str)
    {
        stringBuilder.append("§2");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel darkPurple(String str)
    {
        stringBuilder.append("§5");
        add(str);

        return this;
    }
    public StringFormattedModel darkPurpleR(String str)
    {
        stringBuilder.append("§5");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel darkRed(String str)
    {
        stringBuilder.append("§4");
        add(str);

        return this;
    }
    public StringFormattedModel darkRedR(String str)
    {
        stringBuilder.append("§4");
        addAndReset(str);

        return this;
    }
    
    public StringFormattedModel gold(int str)
    {
        stringBuilder.append("§6");
        add(str);
        
        return this;
    }
    public StringFormattedModel gold(String str)
    {
        stringBuilder.append("§6");
        add(str);

        return this;
    }
    public StringFormattedModel goldR(String str)
    {
        stringBuilder.append("§6");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel gray(String str)
    {
        stringBuilder.append("§7");
        add(str);

        return this;
    }
    public StringFormattedModel gray(int str)
    {
        stringBuilder.append("§7");
        add(str);
        
        return this;
    }
    public StringFormattedModel grayR(String str)
    {
        stringBuilder.append("§7");
        addAndReset(str);

        return this;
    }
    
    public StringFormattedModel green()
    {
        stringBuilder.append("§a");
        
        return this;
    }
    public StringFormattedModel green(int str)
    {
        stringBuilder.append("§a");
        add(str);
        
        return this;
    }
    public StringFormattedModel green(String str)
    {
        stringBuilder.append("§a");
        add(str);

        return this;
    }
    public StringFormattedModel greenR(String str)
    {
        stringBuilder.append("§a");
        addAndReset(str);

        return this;
    }
    public StringFormattedModel greenR(int i)
    {
        stringBuilder.append("§a");
        addAndReset(i);

        return this;
    }

    public StringFormattedModel lightPurple(String str)
    {
        stringBuilder.append("§d");
        add(str);

        return this;
    }
    public StringFormattedModel lightPurpleR(String str)
    {
        stringBuilder.append("§d");
        addAndReset(str);

        return this;
    }
    
    public StringFormattedModel MinecoinGold(int str)
    {
        stringBuilder.append("§g");
        add(str);
        
        return this;
    }
    public StringFormattedModel MinecoinGold(String str)
    {
        stringBuilder.append("§g");
        add(str);

        return this;
    }
    public StringFormattedModel MinecoinGoldR(String str)
    {
        stringBuilder.append("§g");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel red(String str)
    {
        stringBuilder.append("§c");
        add(str);

        return this;
    }
    
    public StringFormattedModel red(int str)
    {
        stringBuilder.append("§c");
        add(str);
        
        return this;
    }
    public StringFormattedModel redR(String str)
    {
        stringBuilder.append("§c");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel white(String str)
    {
        stringBuilder.append("§f");
        add(str);

        return this;
    }
    public StringFormattedModel whiteR(String str)
    {
        stringBuilder.append("§f");
        addAndReset(str);

        return this;
    }

    public StringFormattedModel yellow()
    {
        stringBuilder.append("§e");

        return this;
    }
    public StringFormattedModel yellow(String str)
    {
        stringBuilder.append("§e");
        add(str);

        return this;
    }
    public StringFormattedModel yellowR(String str)
    {
        stringBuilder.append("§e");
        addAndReset(str);

        return this;
    }

    private StringFormattedModel addAndReset(String str)
    {
        add(str);
        stringBuilder.append("§r");

        return this;
    }
    private StringFormattedModel addAndReset(int i)
    {
        add(i);
        stringBuilder.append("§r");

        return this;
    }

    public StringFormattedModel add(String str)
    {
        stringBuilder.append(str);

        return this;
    }
    public StringFormattedModel add(int i)
    {
        stringBuilder.append(i);

        return this;
    }
    public StringFormattedModel add(StringBuilder strBuilder)
    {
        stringBuilder.append(strBuilder);

        return this;
    }
    public StringFormattedModel add(StringFormattedModel strFormatted)
    {
        stringBuilder.append(strFormatted.stringBuilder);

        return this;
    }

    public StringFormattedModel insert(int i, String str)
    {
        stringBuilder.insert(i, str);

        return this;
    }
    public StringFormattedModel insert(int i, StringBuilder chatColor)
    {
        stringBuilder.insert(i, chatColor);

        return this;
    }
    public StringFormattedModel insert(int i, int value)
    {
        stringBuilder.insert(i, value);

        return this;
    }

    public StringFormattedModel reset()
    {
        stringBuilder.append("§r");

        return this;
    }

    public StringFormattedModel nl()
    {
        stringBuilder.append("\n");

        return this;
    }

    public StringFormattedModel bold()
    {
        stringBuilder.append("§l");

        return this;
    }
    public StringFormattedModel italic()
    {
        stringBuilder.append("§o");

        return this;
    }

    public boolean isNullOrEmpty()
    {
        return stringBuilder == null || stringBuilder.isEmpty();
    }

    public String toString()
    {
        return stringBuilder.toString();
    }

    public StringFormattedModel formattedCommand(String command)
    {
        yellow();
        bold();
        add(command);
        reset();

        return this;
    }

    public StringFormattedModel formattedCommandDescription(String description)
    {
        italic();
        stringBuilder.append(description);
        reset();

        return this;
    }

    public StringFormattedModel formattedCommandWithDescription(String command, String description)
    {
        formattedCommand(command);
        stringBuilder.append(": ");
        formattedCommandDescription(description);

        return this;
    }
}
