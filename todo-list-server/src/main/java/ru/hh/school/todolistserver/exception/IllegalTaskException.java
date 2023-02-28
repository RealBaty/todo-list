package ru.hh.school.todolistserver.exception;

public class IllegalTaskException extends IllegalArgumentException{
    public IllegalTaskException(String s){
        super(s);
    }
}
