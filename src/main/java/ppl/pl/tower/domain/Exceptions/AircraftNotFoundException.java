package ppl.pl.tower.domain.Exceptions;

import java.util.function.Supplier;

public class AircraftNotFoundException extends RuntimeException{
    public AircraftNotFoundException(String message){
        super(message);
    }
}
