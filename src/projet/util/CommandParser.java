package projet.util;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    private final List<String> parameters;

    public CommandParser(){
        parameters = new ArrayList<>();
    }

    //Split a line in words and add them to an array List.
    public void parse (String line){
        parameters.clear();
        for(String word : line.trim().split(" ")){
            if (!word.isEmpty() || !word.isBlank())
                parameters.add(word.replaceAll("\n",""));
        }
    }

    public String getStringParameter(int index){
        return parameters.get(index);
    }

    //Join parameters words as a string from ebeginIndex to endIndex.
    public String joinParameters(int beginIndex, int endIndex){
        StringBuilder builder = new StringBuilder();
        for (int i = beginIndex; i < endIndex; i++){
            if (i!= beginIndex) builder.append(" ");
            builder.append(parameters.get(i));
        }

        return builder.toString();
    }

    public Integer getParameterCount(){
        return parameters.size();
    }

    public Integer getIntParameter(int index){
        return Integer.parseInt(parameters.get(index));
    }
}
