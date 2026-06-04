package de.ea234.aoc2017.day25;

import java.util.List;

public class Day25Action
{
  private int    current_value  = 0;

  private int    write_value    = 0;

  private char   move_direction = 0;

  private String continue_state = "";

  public Day25Action( List< String > pListInput, int pIndex )
  {
    current_value    = pListInput.get( pIndex     ).trim().charAt( 24 ) == '0' ? 0 : 1;

    write_value      = pListInput.get( pIndex + 1 ).trim().charAt( 18 ) == '0' ? 0 : 1;

    move_direction   = pListInput.get( pIndex + 2 ).indexOf( "right" ) > 0 ? 'r' : 'l';

    continue_state   = "" + pListInput.get( pIndex + 3 ).trim().charAt( 22 );
  }

  public int getCurrentValue()
  {
    return current_value;
  }

  public int getWriteValue()
  {
    return write_value;
  }

  public char getMoveDirection()
  {
    return move_direction;
  }

  public String getContinueState()
  {
    return continue_state;
  }

  public String toString()
  {
    return "Value " + current_value + " Write " + write_value + " Move " + move_direction + " Cont " + continue_state;
  }
}
