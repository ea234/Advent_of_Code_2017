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

  int    m_tick_index     = 0;

  long[] m_tick_array     = new long[ 200 ];

  int    m_index          = 0;

  String m_input          = "";

  public Day20Particle( int pIndex, String pInput )
  {
    m_index = pIndex;
    m_input = pInput;

    List< Long > list = new ArrayList< Long >();

    Matcher m = Pattern.compile( "-?\\d+" ).matcher( pInput );

    while ( m.find() )
    {
      list.add( Long.valueOf( m.group() ) );
    }

    long[] input_values = list.stream().mapToLong( long_value -> ( (Long) long_value ).longValue() ).toArray();

    //System.out.println( pInput );
    //System.out.println( java.util.Arrays.toString( input_values ) );

    m_x_position = input_values[ 0 ];
    m_y_position = input_values[ 1 ];
    m_z_position = input_values[ 2 ];

    m_x_velocity = input_values[ 3 ];
    m_y_velocity = input_values[ 4 ];
    m_z_velocity = input_values[ 5 ];

    m_x_acceleration = input_values[ 6 ];
    m_y_acceleration = input_values[ 7 ];
    m_z_acceleration = input_values[ 8 ];

    reset();
  }

  public int getIndex()
  {
    return m_index;
  }

  public String getInput()
  {
    return m_input;
  }

  public void reset()
  {
    for ( int index = 0; index < m_tick_array.length; index++ )
    {
      m_tick_array[ index ] = Long.MAX_VALUE;
    }
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

    m_tick_index++;

    if ( m_tick_index == m_tick_array.length )
    {
      m_tick_index = 0;
    }

    m_tick_array[ m_tick_index ] = getManhattenDistance();
  }

  public long getManhattenDistance()
  {
    /*
     * Calculate the Manhattan distance, which in this situation is simply 
     * the sum of the absolute values of a particle's X, Y, and Z position.
     */
    return Math.abs( m_x_position ) + Math.abs( m_y_position ) + Math.abs( m_z_position );
  }

  public long getAvgManhattenDistance()
  {
    long sum_x = 0;

    for ( int index = 0; index < m_tick_array.length; index++ )
    {
      sum_x += m_tick_array[ index ];
    }

    return sum_x / m_tick_array.length;
  }

  public long getXPosition()
  {
    return m_x_position;
  }

  public void setXPosition( long pXPosition )
  {
    m_x_position = pXPosition;
  }

  public long getYPosition()
  {
    return m_y_position;
  }

  public void setYPosition( long pYPosition )
  {
    m_y_position = pYPosition;
  }

  public long getZPosition()
  {
    return m_z_position;
  }

  public void setZPosition( long pZPosition )
  {
    m_z_position = pZPosition;
  }

  public long getXAcceleration()
  {
    return m_x_acceleration;
  }

  public void setXAcceleration( long pXAcceleration )
  {
    m_x_acceleration = pXAcceleration;
  }

  public long getYAcceleration()
  {
    return m_y_acceleration;
  }

  public void setYAcceleration( long pYAcceleration )
  {
    m_y_acceleration = pYAcceleration;
  }

  public long getZAcceleration()
  {
    return m_z_acceleration;
  }

  public void setZAcceleration( long pZAcceleration )
  {
    m_z_acceleration = pZAcceleration;
  }

  public long getXVelocity()
  {
    return m_x_velocity;
  }

  public void setXVelocity( long pXVelocity )
  {
    m_x_velocity = pXVelocity;
  }

  public long getYVelocity()
  {
    return m_y_velocity;
  }

  public void setYVelocity( long pYVelocity )
  {
    m_y_velocity = pYVelocity;
  }

  public long getZVelocity()
  {
    return m_z_velocity;
  }

  public void setZVelocity( long pZVelocity )
  {
    m_z_velocity = pZVelocity;
  }

  public String toString()
  {
    return String.format( " X  p %9d  v %9d  a %9d    Y  p %9d  v %9d  a %9d   Z  p %9d  v %9d  a %9d ", m_x_position, m_x_velocity, m_x_acceleration, m_y_position, m_y_velocity, m_y_acceleration, m_z_position, m_z_velocity, m_z_acceleration );
  }
}
