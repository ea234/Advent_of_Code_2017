package de.ea234.aoc2017.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day20Particle
{
  long   m_x_position     = 0l;

  long   m_y_position     = 0l;

  long   m_z_position     = 0l;

  long   m_x_velocity     = 0l;

  long   m_y_velocity     = 0l;

  long   m_z_velocity     = 0l;

  long   m_x_acceleration = 0l;

  long   m_y_acceleration = 0l;

  long   m_z_acceleration = 0l;

  int    m_id             = 0;

  String m_input          = "";

  public Day20Particle( int pID, String pInput )
  {
    m_id = pID;

    m_input = pInput;

    reset();
  }

  public void reset()
  {
    List< Long > temp_input_list = new ArrayList< Long >();

    Matcher reg_ex_matcher = Pattern.compile( "-?\\d+" ).matcher( m_input );

    while ( reg_ex_matcher.find() )
    {
      temp_input_list.add( Long.valueOf( reg_ex_matcher.group() ) );
    }

    long[] input_values = temp_input_list.stream().mapToLong( long_value -> ( (Long) long_value ).longValue() ).toArray();

    //System.out.println( pInput );
    //System.out.println( java.util.Arrays.toString( input_values ) );

    m_x_position     = input_values[ 0 ];
    m_y_position     = input_values[ 1 ];
    m_z_position     = input_values[ 2 ];

    m_x_velocity     = input_values[ 3 ];
    m_y_velocity     = input_values[ 4 ];
    m_z_velocity     = input_values[ 5 ];

    m_x_acceleration = input_values[ 6 ];
    m_y_acceleration = input_values[ 7 ];
    m_z_acceleration = input_values[ 8 ];
  }

  public void doTick()
  {
    /*
     * Increase the X velocity by the X acceleration.
     */
    m_x_velocity += m_x_acceleration;

    /*
     * Increase the Y velocity by the Y acceleration.
     */
    m_y_velocity += m_y_acceleration;

    /*
     * Increase the Z velocity by the Z acceleration.
     */
    m_z_velocity += m_z_acceleration;

    /*
     * Increase the X position by the X velocity.
     */
    m_x_position += m_x_velocity;

    /*
     * Increase the Y position by the Y velocity.
     */
    m_y_position += m_y_velocity;

    /*
     * Increase the Z position by the Z velocity.
     */
    m_z_position += m_z_velocity;
  }

  public long getManhattenDistance()
  {
    /*
     * Calculate the Manhattan distance, which in this situation is simply 
     * the sum of the absolute values of a particle's X, Y, and Z position.
     */
    return Math.abs( m_x_position ) + Math.abs( m_y_position ) + Math.abs( m_z_position );
  }

  public String getKey()
  {
    return "X" + m_x_position + "Y" + m_y_position + "Z" + m_z_position;
  }

  public boolean isMyID( long pID )
  {
    return m_id == pID;
  }

  public boolean isNotMyID( String pID )
  {
    return m_id != Integer.parseInt( pID );
  }

  public long getXPosition()
  {
    return m_x_position;
  }

  public long getYPosition()
  {
    return m_y_position;
  }

  public long getZPosition()
  {
    return m_z_position;
  }

  public int getID()
  {
    return m_id;
  }

  public String getInput()
  {
    return m_input;
  }

  public String toString()
  {
    return String.format( " X  p %9d  v %9d  a %9d    Y  p %9d  v %9d  a %9d   Z  p %9d  v %9d  a %9d ", m_x_position, m_x_velocity, m_x_acceleration, m_y_position, m_y_velocity, m_y_acceleration, m_z_position, m_z_velocity, m_z_acceleration );
  }
}
