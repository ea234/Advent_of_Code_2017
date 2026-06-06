package de.ea234.aoc2017.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 20: Particle Swarm ---
 * https://adventofcode.com/2017/day/22
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kz6ik/2017_day_20_solutions/
 * 
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance          498497 
 * 
 * Minimum Manhatten Distance 0  Definition  p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
 * 
 * Minimum Manhatten Distance 0  Cur Values   X  p   -499496  v      -999  a        -1    Y  p         0  v         0  a         0   Z  p         0  v         0  a         0 
 * 
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * 
 * #### Particle   0  MH Distance        12036890 
 * #### Particle   1  MH Distance         5474978 
 * #### Particle   2  MH Distance         3952345 
 * #### Particle  25  MH Distance         2612750 
 * #### Particle  38  MH Distance         1699402 
 * #### Particle  71  MH Distance         1698357 
 * #### Particle 279  MH Distance          647520 
 * #### Particle 308  MH Distance          628116 
 * 
 * Minimum Manhatten Distance 308  Definition  p=<2978,2082,4280>, v=<-135,-88,-178>, a=<1,0,0>
 * 
 * Minimum Manhatten Distance 308  Cur Values   X  p    369344  v       866  a         1    Y  p    -86006  v       -88  a         0   Z  p   -173898  v      -178  a         0 
 * 
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 308
 * 
 * </pre> 
 */
public class Day20_ParticleSwarm
{
  public static void main( String[] args )
  {
    String test_input = "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>;p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>";

    calculatePart01( test_input, 0, true );
    calculate01( getListProd(), 0, true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pBursts, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pBursts, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pBursts, boolean pKnzDebug )
  {
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );

    /*
     * *******************************************************************************************************
     * Creating the list of particles
     * *******************************************************************************************************
     */

    List< Day20Particle > list_particles = new ArrayList< Day20Particle >();

    int particle_index = 0;

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        list_particles.add( new Day20Particle( particle_index, input_str.trim() ) );

        particle_index++;
      }
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    int max_tick_for_simulation_1 = 1000;

    for ( int count_ticks = 0; count_ticks < max_tick_for_simulation_1; count_ticks++ )
    {
      for ( Day20Particle cur_particle : list_particles )
      {
        cur_particle.doTick();
      }
    }

    /*
     * *******************************************************************************************************
     * Find the minimum Manhatten-Distance
     * *******************************************************************************************************
     */

    wl( "" );

    int min_index = 9999;
    long min_mh_distance = Long.MAX_VALUE;

    for ( Day20Particle cur_particle : list_particles )
    {
      if ( cur_particle.getManhattenDistance() < min_mh_distance )
      {
        wl( String.format( "#### Particle %3d  MH Distance %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance() ) );

        min_mh_distance = cur_particle.getManhattenDistance();

        min_index = cur_particle.getIndex();
      }
      else
      {
        //wl( String.format( "     Particle %3d  MH Distance %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance() ) );
      }
    }

    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Definition  " + list_particles.get( min_index ).getInput() );
    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Cur Values  " + list_particles.get( min_index ).toString() );
    wl( "" );

    /*
     * *******************************************************************************************************
     * Reset for part 2
     * *******************************************************************************************************
     */

    Properties x_prop = new Properties();

    for ( Day20Particle cur_particle : list_particles )
    {
      cur_particle.reset();

      String collision_before_tick = x_prop.getProperty( cur_particle.getKey() );

      if ( ( collision_before_tick != null ) && ( cur_particle.isNotMyIndex( collision_before_tick ) ) )
      {
        wl( "Debug - A - Collision before " + cur_particle.getKey() + " with " + collision_before_tick );
      }

      x_prop.setProperty( cur_particle.getKey(), "" + cur_particle.getIndex() );
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    // 534 - 450
    int max_tick_for_simulation_2 = 10_000;

    String dbg_str = "";

    for ( int count_ticks = 0; count_ticks < max_tick_for_simulation_2; count_ticks++ )
    {
      dbg_str = "\n\n---------- Tick Nr " + count_ticks + "------------------------------------\n";

      List< Day20Particle > deletion_list = new ArrayList< Day20Particle >();

      for ( Day20Particle cur_particle : list_particles )
      {
        String collision_before_tick = x_prop.getProperty( cur_particle.getKey() );

        if ( ( collision_before_tick != null ) && ( cur_particle.isNotMyIndex( collision_before_tick ) ) )
        {
          wl( "Debug - B - Collision before " + cur_particle.getKey() + " ID " + cur_particle.getIndex() + " with ID " + collision_before_tick + " " + count_ticks );
        }
        else
        {
          x_prop.remove( cur_particle.getKey() );

          cur_particle.doTick();

          String collision_after_tick = x_prop.getProperty( cur_particle.getKey() );

          if ( ( collision_after_tick != null ) && ( cur_particle.isNotMyIndex( collision_after_tick ) ) )
          {

            wl( dbg_str + String.format( "Collision after %-15s   ID %4d with ID %4s  Tick %5d ", cur_particle.getKey(), cur_particle.getIndex(), collision_after_tick, count_ticks ) );

            dbg_str = "";
            if ( deletion_list.contains( cur_particle ) == false )
            {
              deletion_list.add( cur_particle );
            }

            int index_particle_b = Integer.valueOf( collision_after_tick ).intValue();

            Day20Particle particle_b = list_particles.get( index_particle_b );

            if ( deletion_list.contains( particle_b ) == false )
            {
              deletion_list.add( particle_b );
            }
          }
          else
          {
            x_prop.setProperty( cur_particle.getKey(), "" + cur_particle.getIndex() );
          }
        }
      }

      if ( deletion_list.size() > 0 )
      {
        for ( Day20Particle cur_particle : deletion_list )
        {
          wl( "    -> REMOVE ID " + cur_particle.getIndex() + " with key " + cur_particle.getKey() );
          x_prop.remove( cur_particle.getKey() );

          list_particles.remove( cur_particle );
        }

        wl( "\nList Particles -" + deletion_list.size() + " = " + list_particles.size() );
      }
    }

    wl( "max_tick_for_simulation " + max_tick_for_simulation_1 );
    wl( "" );
    wl( "" );
    wl( "Result Part 1 " + min_index );
    wl( "Result Part 1 " + list_particles.size() );
    wl( "" );

  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day20_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}

/*
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance          498497 
 * 
 * Minimum Manhatten Distance 0  Definition  p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
 * 
 * Minimum Manhatten Distance 0  Cur Values   X  p   -498497  v      -998  a        -1    Y  p         0  v         0  a         0   Z  p         0  v         0  a         0 
 * 
 * 
 * 
 * ---------- Tick Nr 0------------------------------------
 * Collision after X4Y0Z0            ID    0 with ID    1  Tick     0 
 *     -> REMOVE ID 0 with key X4Y0Z0
 *     -> REMOVE ID 1 with key X2Y0Z0
 * 
 * List Particles -2 = 0
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 0
 * Result Part 1 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance        12036890 
 * #### Particle   1  MH Distance         5474978 
 * #### Particle   2  MH Distance         3952345 
 * #### Particle  25  MH Distance         2612750 
 * #### Particle  38  MH Distance         1699402 
 * #### Particle  71  MH Distance         1698357 
 * #### Particle 279  MH Distance          647520 
 * #### Particle 308  MH Distance          628116 
 * 
 * Minimum Manhatten Distance 308  Definition  p=<2978,2082,4280>, v=<-135,-88,-178>, a=<1,0,0>
 * 
 * Minimum Manhatten Distance 308  Cur Values   X  p    368478  v       865  a         1    Y  p    -85918  v       -88  a         0   Z  p   -173720  v      -178  a         0 
 * 
 * 
 * 
 * ---------- Tick Nr 9------------------------------------
 * Collision after X-19Y29Z22        ID   67 with ID   66  Tick     9 
 * Collision after X-19Y29Z22        ID   68 with ID   66  Tick     9 
 * Collision after X-19Y29Z22        ID   69 with ID   66  Tick     9 
 * Collision after X-19Y29Z22        ID   70 with ID   66  Tick     9 
 * Collision after X-19Y29Z22        ID   71 with ID   66  Tick     9 
 * Collision after X-19Y29Z22        ID   72 with ID   66  Tick     9 
 * Collision after X2Y29Z44          ID  349 with ID  348  Tick     9 
 * Collision after X2Y29Z44          ID  350 with ID  348  Tick     9 
 * Collision after X2Y29Z44          ID  351 with ID  348  Tick     9 
 * Collision after X2Y29Z44          ID  352 with ID  348  Tick     9 
 * Collision after X2Y29Z44          ID  353 with ID  348  Tick     9 
 *     -> REMOVE ID 67 with key X-19Y29Z22
 *     -> REMOVE ID 66 with key X-19Y29Z22
 *     -> REMOVE ID 68 with key X-19Y29Z22
 *     -> REMOVE ID 69 with key X-19Y29Z22
 *     -> REMOVE ID 70 with key X-19Y29Z22
 *     -> REMOVE ID 71 with key X-19Y29Z22
 *     -> REMOVE ID 72 with key X-19Y29Z22
 *     -> REMOVE ID 349 with key X2Y29Z44
 *     -> REMOVE ID 348 with key X2Y29Z44
 *     -> REMOVE ID 350 with key X2Y29Z44
 *     -> REMOVE ID 351 with key X2Y29Z44
 *     -> REMOVE ID 352 with key X2Y29Z44
 *     -> REMOVE ID 353 with key X2Y29Z44
 * 
 * List Particles -13 = 987
 * 
 * 
 * ---------- Tick Nr 10------------------------------------
 * Collision after X12Y29Z9          ID  301 with ID  300  Tick    10 
 * Collision after X12Y29Z9          ID  302 with ID  300  Tick    10 
 * Collision after X21Y3Z26          ID  341 with ID  340  Tick    10 
 * Collision after X21Y3Z26          ID  342 with ID  340  Tick    10 
 * Collision after X21Y3Z26          ID  343 with ID  340  Tick    10 
 * Collision after X21Y3Z26          ID  344 with ID  340  Tick    10 
 * Collision after X21Y3Z26          ID  345 with ID  340  Tick    10 
 * Collision after X21Y3Z26          ID  346 with ID  340  Tick    10 
 * Collision after X21Y3Z26          ID  347 with ID  340  Tick    10 
 *     -> REMOVE ID 301 with key X12Y29Z9
 *     -> REMOVE ID 307 with key X-534Y425Z1061
 *     -> REMOVE ID 302 with key X12Y29Z9
 *     -> REMOVE ID 341 with key X21Y3Z26
 *     -> REMOVE ID 347 with key X21Y3Z26
 *     -> REMOVE ID 342 with key X21Y3Z26
 *     -> REMOVE ID 343 with key X21Y3Z26
 *     -> REMOVE ID 344 with key X21Y3Z26
 *     -> REMOVE ID 345 with key X21Y3Z26
 *     -> REMOVE ID 346 with key X21Y3Z26
 * 
 * List Particles -10 = 977
 * 
 * 
 * ---------- Tick Nr 12------------------------------------
 * Collision after X46Y11Z14         ID  355 with ID  354  Tick    12 
 * Collision after X46Y11Z14         ID  356 with ID  354  Tick    12 
 * Collision after X46Y11Z14         ID  357 with ID  354  Tick    12 
 * Collision after X46Y11Z14         ID  358 with ID  354  Tick    12 
 * Collision after X46Y11Z14         ID  359 with ID  354  Tick    12 
 * Collision after X46Y11Z14         ID  360 with ID  354  Tick    12 
 *     -> REMOVE ID 355 with key X46Y11Z14
 *     -> REMOVE ID 377 with key X1381Y1591Z1097
 *     -> REMOVE ID 356 with key X46Y11Z14
 *     -> REMOVE ID 357 with key X46Y11Z14
 *     -> REMOVE ID 358 with key X46Y11Z14
 *     -> REMOVE ID 359 with key X46Y11Z14
 *     -> REMOVE ID 360 with key X46Y11Z14
 * 
 * List Particles -7 = 970
 * 
 * 
 * ---------- Tick Nr 13------------------------------------
 * Collision after X27Y24Z24         ID   51 with ID   50  Tick    13 
 * Collision after X27Y24Z24         ID   52 with ID   50  Tick    13 
 * Collision after X27Y24Z24         ID   53 with ID   50  Tick    13 
 * Collision after X27Y24Z24         ID   54 with ID   50  Tick    13 
 * Collision after X27Y24Z24         ID   55 with ID   50  Tick    13 
 * Collision after X2Y12Z22          ID  131 with ID  130  Tick    13 
 * Collision after X2Y12Z22          ID  132 with ID  130  Tick    13 
 * Collision after X2Y12Z22          ID  133 with ID  130  Tick    13 
 * Collision after X2Y12Z22          ID  134 with ID  130  Tick    13 
 * Collision after X2Y12Z22          ID  135 with ID  130  Tick    13 
 * Collision after X18Y6Z30          ID  137 with ID  136  Tick    13 
 *     -> REMOVE ID 51 with key X27Y24Z24
 *     -> REMOVE ID 50 with key X27Y24Z24
 *     -> REMOVE ID 52 with key X27Y24Z24
 *     -> REMOVE ID 53 with key X27Y24Z24
 *     -> REMOVE ID 54 with key X27Y24Z24
 *     -> REMOVE ID 55 with key X27Y24Z24
 *     -> REMOVE ID 131 with key X2Y12Z22
 *     -> REMOVE ID 137 with key X18Y6Z30
 *     -> REMOVE ID 132 with key X2Y12Z22
 *     -> REMOVE ID 133 with key X2Y12Z22
 *     -> REMOVE ID 134 with key X2Y12Z22
 *     -> REMOVE ID 135 with key X2Y12Z22
 *     -> REMOVE ID 143 with key X-4321Y6002Z1846
 * 
 * List Particles -13 = 957
 * 
 * 
 * ---------- Tick Nr 14------------------------------------
 * Collision after X-34Y66Z35        ID  109 with ID  108  Tick    14 
 * Collision after X18Y3Z32          ID  123 with ID  122  Tick    14 
 * Collision after X18Y3Z32          ID  124 with ID  122  Tick    14 
 * Collision after X18Y3Z32          ID  125 with ID  122  Tick    14 
 * Collision after X18Y3Z32          ID  126 with ID  122  Tick    14 
 * Collision after X18Y3Z32          ID  127 with ID  122  Tick    14 
 * Collision after X18Y3Z32          ID  128 with ID  122  Tick    14 
 * Collision after X18Y3Z32          ID  129 with ID  122  Tick    14 
 * Collision after X14Y34Z11         ID  178 with ID  177  Tick    14 
 * Collision after X39Y5Z5           ID  331 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  332 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  333 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  334 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  335 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  336 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  337 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  338 with ID  330  Tick    14 
 * Collision after X39Y5Z5           ID  339 with ID  330  Tick    14 
 *     -> REMOVE ID 109 with key X-34Y66Z35
 *     -> REMOVE ID 121 with key X6826Y3978Z2655
 *     -> REMOVE ID 123 with key X18Y3Z32
 *     -> REMOVE ID 141 with key X-588Y2487Z681
 *     -> REMOVE ID 124 with key X18Y3Z32
 *     -> REMOVE ID 125 with key X18Y3Z32
 *     -> REMOVE ID 126 with key X18Y3Z32
 *     -> REMOVE ID 127 with key X18Y3Z32
 *     -> REMOVE ID 128 with key X18Y3Z32
 *     -> REMOVE ID 129 with key X18Y3Z32
 *     -> REMOVE ID 178 with key X14Y34Z11
 *     -> REMOVE ID 197 with key X1314Y3920Z581
 *     -> REMOVE ID 331 with key X39Y5Z5
 *     -> REMOVE ID 372 with key X2631Y1814Z1723
 *     -> REMOVE ID 332 with key X39Y5Z5
 *     -> REMOVE ID 333 with key X39Y5Z5
 *     -> REMOVE ID 334 with key X39Y5Z5
 *     -> REMOVE ID 335 with key X39Y5Z5
 *     -> REMOVE ID 336 with key X39Y5Z5
 *     -> REMOVE ID 337 with key X39Y5Z5
 *     -> REMOVE ID 338 with key X39Y5Z5
 *     -> REMOVE ID 339 with key X39Y5Z5
 * 
 * List Particles -22 = 935
 * 
 * 
 * ---------- Tick Nr 15------------------------------------
 * Collision after X43Y30Z12         ID  385 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  386 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  387 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  388 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  389 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  390 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  391 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  392 with ID  384  Tick    15 
 * Collision after X43Y30Z12         ID  393 with ID  384  Tick    15 
 * Collision after X-29Y28Z7         ID  453 with ID  452  Tick    15 
 * Collision after X-29Y28Z7         ID  454 with ID  452  Tick    15 
 * Collision after X-17Y27Z49        ID  464 with ID  463  Tick    15 
 * Collision after X-17Y27Z49        ID  465 with ID  463  Tick    15 
 * Collision after X-17Y27Z49        ID  466 with ID  463  Tick    15 
 *     -> REMOVE ID 385 with key X43Y30Z12
 *     -> REMOVE ID 449 with key X426Y606Z762
 *     -> REMOVE ID 386 with key X43Y30Z12
 *     -> REMOVE ID 387 with key X43Y30Z12
 *     -> REMOVE ID 388 with key X43Y30Z12
 *     -> REMOVE ID 389 with key X43Y30Z12
 *     -> REMOVE ID 390 with key X43Y30Z12
 *     -> REMOVE ID 391 with key X43Y30Z12
 *     -> REMOVE ID 392 with key X43Y30Z12
 *     -> REMOVE ID 393 with key X43Y30Z12
 *     -> REMOVE ID 453 with key X-29Y28Z7
 *     -> REMOVE ID 517 with key X-1421Y626Z6172
 *     -> REMOVE ID 454 with key X-29Y28Z7
 *     -> REMOVE ID 464 with key X-17Y27Z49
 *     -> REMOVE ID 528 with key X-2715Y1123Z4954
 *     -> REMOVE ID 465 with key X-17Y27Z49
 *     -> REMOVE ID 466 with key X-17Y27Z49
 * 
 * List Particles -17 = 918
 * 
 * 
 * ---------- Tick Nr 16------------------------------------
 * Collision after X10Y28Z24         ID  167 with ID  166  Tick    16 
 * Collision after X10Y28Z24         ID  168 with ID  166  Tick    16 
 * Collision after X10Y28Z24         ID  169 with ID  166  Tick    16 
 * Collision after X10Y28Z24         ID  170 with ID  166  Tick    16 
 * Collision after X10Y28Z24         ID  171 with ID  166  Tick    16 
 * Collision after X-34Y57Z29        ID  327 with ID  326  Tick    16 
 * Collision after X-34Y57Z29        ID  328 with ID  326  Tick    16 
 * Collision after X-34Y57Z29        ID  329 with ID  326  Tick    16 
 * Collision after X-45Y18Z11        ID  473 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  474 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  475 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  476 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  477 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  478 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  479 with ID  472  Tick    16 
 * Collision after X-45Y18Z11        ID  480 with ID  472  Tick    16 
 *     -> REMOVE ID 167 with key X10Y28Z24
 *     -> REMOVE ID 198 with key X155Y3206Z1494
 *     -> REMOVE ID 168 with key X10Y28Z24
 *     -> REMOVE ID 169 with key X10Y28Z24
 *     -> REMOVE ID 170 with key X10Y28Z24
 *     -> REMOVE ID 171 with key X10Y28Z24
 *     -> REMOVE ID 327 with key X-34Y57Z29
 *     -> REMOVE ID 400 with key X-405Y511Z546
 *     -> REMOVE ID 328 with key X-34Y57Z29
 *     -> REMOVE ID 329 with key X-34Y57Z29
 *     -> REMOVE ID 473 with key X-45Y18Z11
 *     -> REMOVE ID 554 with key X-2561Y5124Z2815
 *     -> REMOVE ID 474 with key X-45Y18Z11
 *     -> REMOVE ID 475 with key X-45Y18Z11
 *     -> REMOVE ID 476 with key X-45Y18Z11
 *     -> REMOVE ID 477 with key X-45Y18Z11
 *     -> REMOVE ID 478 with key X-45Y18Z11
 *     -> REMOVE ID 479 with key X-45Y18Z11
 *     -> REMOVE ID 480 with key X-45Y18Z11
 * 
 * List Particles -19 = 899
 * 
 * 
 * ---------- Tick Nr 17------------------------------------
 * Collision after X-27Y0Z4          ID   23 with ID   22  Tick    17 
 * Collision after X-27Y0Z4          ID   24 with ID   22  Tick    17 
 * Collision after X-18Y19Z50        ID   34 with ID   33  Tick    17 
 * Collision after X-18Y19Z50        ID   35 with ID   33  Tick    17 
 * Collision after X-18Y19Z50        ID   36 with ID   33  Tick    17 
 * Collision after X-18Y19Z50        ID   37 with ID   33  Tick    17 
 * Collision after X-18Y19Z50        ID   38 with ID   33  Tick    17 
 * Collision after X-11Y14Z31        ID  287 with ID  286  Tick    17 
 * Collision after X-11Y14Z31        ID  288 with ID  286  Tick    17 
 *     -> REMOVE ID 23 with key X-27Y0Z4
 *     -> REMOVE ID 22 with key X-27Y0Z4
 *     -> REMOVE ID 24 with key X-27Y0Z4
 *     -> REMOVE ID 34 with key X-18Y19Z50
 *     -> REMOVE ID 33 with key X-18Y19Z50
 *     -> REMOVE ID 35 with key X-18Y19Z50
 *     -> REMOVE ID 36 with key X-18Y19Z50
 *     -> REMOVE ID 37 with key X-18Y19Z50
 *     -> REMOVE ID 38 with key X-18Y19Z50
 *     -> REMOVE ID 287 with key X-11Y14Z31
 *     -> REMOVE ID 330 with key X432Y770Z188
 *     -> REMOVE ID 288 with key X-11Y14Z31
 * 
 * List Particles -12 = 887
 * 
 * 
 * ---------- Tick Nr 18------------------------------------
 * Collision after X-62Y18Z0         ID  194 with ID  193  Tick    18 
 * Collision after X-62Y18Z0         ID  195 with ID  193  Tick    18 
 * Collision after X-2Y9Z8           ID  205 with ID  204  Tick    18 
 * Collision after X-2Y9Z8           ID  206 with ID  204  Tick    18 
 * Collision after X-2Y9Z8           ID  207 with ID  204  Tick    18 
 * Collision after X-2Y9Z8           ID  208 with ID  204  Tick    18 
 * Collision after X-2Y9Z8           ID  209 with ID  204  Tick    18 
 * Collision after X-2Y9Z8           ID  210 with ID  204  Tick    18 
 * Collision after X-2Y9Z8           ID  211 with ID  204  Tick    18 
 * Collision after X-9Y44Z53         ID  266 with ID  265  Tick    18 
 * Collision after X-9Y44Z53         ID  267 with ID  265  Tick    18 
 * Collision after X-9Y44Z53         ID  268 with ID  265  Tick    18 
 * Collision after X18Y16Z58         ID  277 with ID  276  Tick    18 
 * Collision after X18Y16Z58         ID  278 with ID  276  Tick    18 
 * Collision after X18Y16Z58         ID  279 with ID  276  Tick    18 
 * Collision after X18Y16Z58         ID  280 with ID  276  Tick    18 
 * Collision after X16Y31Z35         ID  456 with ID  455  Tick    18 
 * Collision after X16Y31Z35         ID  457 with ID  455  Tick    18 
 * Collision after X16Y31Z35         ID  458 with ID  455  Tick    18 
 * Collision after X16Y31Z35         ID  459 with ID  455  Tick    18 
 * Collision after X16Y31Z35         ID  460 with ID  455  Tick    18 
 * Collision after X16Y31Z35         ID  461 with ID  455  Tick    18 
 * Collision after X16Y31Z35         ID  462 with ID  455  Tick    18 
 * Collision after X-12Y57Z7         ID  468 with ID  467  Tick    18 
 * Collision after X-12Y57Z7         ID  469 with ID  467  Tick    18 
 * Collision after X-12Y57Z7         ID  470 with ID  467  Tick    18 
 * Collision after X-12Y57Z7         ID  471 with ID  467  Tick    18 
 * Collision after X7Y27Z26          ID  488 with ID  487  Tick    18 
 * Collision after X7Y27Z26          ID  489 with ID  487  Tick    18 
 *     -> REMOVE ID 194 with key X-62Y18Z0
 *     -> REMOVE ID 240 with key X-1681Y1005Z4221
 *     -> REMOVE ID 195 with key X-62Y18Z0
 *     -> REMOVE ID 205 with key X-2Y9Z8
 *     -> REMOVE ID 251 with key X-347Y2062Z1016
 *     -> REMOVE ID 206 with key X-2Y9Z8
 *     -> REMOVE ID 207 with key X-2Y9Z8
 *     -> REMOVE ID 208 with key X-2Y9Z8
 *     -> REMOVE ID 209 with key X-2Y9Z8
 *     -> REMOVE ID 210 with key X-2Y9Z8
 *     -> REMOVE ID 211 with key X-2Y9Z8
 *     -> REMOVE ID 266 with key X-9Y44Z53
 *     -> REMOVE ID 317 with key X1516Y3761Z695
 *     -> REMOVE ID 267 with key X-9Y44Z53
 *     -> REMOVE ID 268 with key X-9Y44Z53
 *     -> REMOVE ID 277 with key X18Y16Z58
 *     -> REMOVE ID 354 with key X409Y128Z47
 *     -> REMOVE ID 278 with key X18Y16Z58
 *     -> REMOVE ID 279 with key X18Y16Z58
 *     -> REMOVE ID 280 with key X18Y16Z58
 *     -> REMOVE ID 456 with key X16Y31Z35
 *     -> REMOVE ID 568 with key X4069Y1769Z4550
 *     -> REMOVE ID 457 with key X16Y31Z35
 *     -> REMOVE ID 458 with key X16Y31Z35
 *     -> REMOVE ID 459 with key X16Y31Z35
 *     -> REMOVE ID 460 with key X16Y31Z35
 *     -> REMOVE ID 461 with key X16Y31Z35
 *     -> REMOVE ID 462 with key X16Y31Z35
 *     -> REMOVE ID 468 with key X-12Y57Z7
 *     -> REMOVE ID 580 with key X5514Y39Z1482
 *     -> REMOVE ID 469 with key X-12Y57Z7
 *     -> REMOVE ID 470 with key X-12Y57Z7
 *     -> REMOVE ID 471 with key X-12Y57Z7
 *     -> REMOVE ID 488 with key X7Y27Z26
 *     -> REMOVE ID 600 with key X-4471Y1460Z3588
 *     -> REMOVE ID 489 with key X7Y27Z26
 * 
 * List Particles -36 = 851
 * 
 * 
 * ---------- Tick Nr 19------------------------------------
 * Collision after X-69Y29Z5         ID  111 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  112 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  113 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  114 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  115 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  116 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  117 with ID  110  Tick    19 
 * Collision after X-69Y29Z5         ID  118 with ID  110  Tick    19 
 * Collision after X-23Y38Z50        ID  180 with ID  179  Tick    19 
 * Collision after X-23Y38Z50        ID  181 with ID  179  Tick    19 
 * Collision after X-23Y38Z50        ID  182 with ID  179  Tick    19 
 * Collision after X-23Y38Z50        ID  183 with ID  179  Tick    19 
 * Collision after X-23Y38Z50        ID  184 with ID  179  Tick    19 
 * Collision after X24Y21Z47         ID  231 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  232 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  233 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  234 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  235 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  236 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  237 with ID  230  Tick    19 
 * Collision after X24Y21Z47         ID  238 with ID  230  Tick    19 
 *     -> REMOVE ID 111 with key X-69Y29Z5
 *     -> REMOVE ID 149 with key X624Y742Z336
 *     -> REMOVE ID 112 with key X-69Y29Z5
 *     -> REMOVE ID 113 with key X-69Y29Z5
 *     -> REMOVE ID 114 with key X-69Y29Z5
 *     -> REMOVE ID 115 with key X-69Y29Z5
 *     -> REMOVE ID 116 with key X-69Y29Z5
 *     -> REMOVE ID 117 with key X-69Y29Z5
 *     -> REMOVE ID 118 with key X-69Y29Z5
 *     -> REMOVE ID 180 with key X-23Y38Z50
 *     -> REMOVE ID 235 with key X24Y21Z47
 *     -> REMOVE ID 181 with key X-23Y38Z50
 *     -> REMOVE ID 182 with key X-23Y38Z50
 *     -> REMOVE ID 183 with key X-23Y38Z50
 *     -> REMOVE ID 184 with key X-23Y38Z50
 *     -> REMOVE ID 231 with key X24Y21Z47
 *     -> REMOVE ID 297 with key X-350Y475Z317
 *     -> REMOVE ID 232 with key X24Y21Z47
 *     -> REMOVE ID 233 with key X24Y21Z47
 *     -> REMOVE ID 234 with key X24Y21Z47
 *     -> REMOVE ID 236 with key X24Y21Z47
 *     -> REMOVE ID 237 with key X24Y21Z47
 *     -> REMOVE ID 238 with key X24Y21Z47
 * 
 * List Particles -23 = 828
 * 
 * 
 * ---------- Tick Nr 20------------------------------------
 * Collision after X-41Y12Z5         ID    9 with ID    8  Tick    20 
 * Collision after X-41Y12Z5         ID   10 with ID    8  Tick    20 
 * Collision after X-41Y12Z5         ID   11 with ID    8  Tick    20 
 * Collision after X-41Y12Z5         ID   12 with ID    8  Tick    20 
 * Collision after X18Y50Z11         ID   57 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   58 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   59 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   60 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   61 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   62 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   63 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   64 with ID   56  Tick    20 
 * Collision after X18Y50Z11         ID   65 with ID   56  Tick    20 
 * Collision after X-24Y26Z25        ID  154 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  155 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  156 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  157 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  158 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  159 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  160 with ID  153  Tick    20 
 * Collision after X-24Y26Z25        ID  161 with ID  153  Tick    20 
 * Collision after X30Y34Z13         ID  282 with ID  281  Tick    20 
 * Collision after X30Y34Z13         ID  283 with ID  281  Tick    20 
 * Collision after X30Y34Z13         ID  284 with ID  281  Tick    20 
 * Collision after X30Y34Z13         ID  285 with ID  281  Tick    20 
 * Collision after X-65Y21Z12        ID  417 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  418 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  419 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  420 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  421 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  422 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  423 with ID  416  Tick    20 
 * Collision after X-65Y21Z12        ID  424 with ID  416  Tick    20 
 *     -> REMOVE ID 9 with key X-41Y12Z5
 *     -> REMOVE ID 8 with key X-41Y12Z5
 *     -> REMOVE ID 10 with key X-41Y12Z5
 *     -> REMOVE ID 11 with key X-41Y12Z5
 *     -> REMOVE ID 12 with key X-41Y12Z5
 *     -> REMOVE ID 57 with key X18Y50Z11
 *     -> REMOVE ID 78 with key X403Y265Z85
 *     -> REMOVE ID 58 with key X18Y50Z11
 *     -> REMOVE ID 59 with key X18Y50Z11
 *     -> REMOVE ID 60 with key X18Y50Z11
 *     -> REMOVE ID 61 with key X18Y50Z11
 *     -> REMOVE ID 62 with key X18Y50Z11
 *     -> REMOVE ID 63 with key X18Y50Z11
 *     -> REMOVE ID 64 with key X18Y50Z11
 *     -> REMOVE ID 65 with key X18Y50Z11
 *     -> REMOVE ID 154 with key X-24Y26Z25
 *     -> REMOVE ID 223 with key X3803Y4655Z6045
 *     -> REMOVE ID 155 with key X-24Y26Z25
 *     -> REMOVE ID 156 with key X-24Y26Z25
 *     -> REMOVE ID 157 with key X-24Y26Z25
 *     -> REMOVE ID 158 with key X-24Y26Z25
 *     -> REMOVE ID 159 with key X-24Y26Z25
 *     -> REMOVE ID 160 with key X-24Y26Z25
 *     -> REMOVE ID 161 with key X-24Y26Z25
 *     -> REMOVE ID 282 with key X30Y34Z13
 *     -> REMOVE ID 420 with key X-65Y21Z12
 *     -> REMOVE ID 283 with key X30Y34Z13
 *     -> REMOVE ID 284 with key X30Y34Z13
 *     -> REMOVE ID 285 with key X30Y34Z13
 *     -> REMOVE ID 417 with key X-65Y21Z12
 *     -> REMOVE ID 587 with key X-1398Y3539Z3607
 *     -> REMOVE ID 418 with key X-65Y21Z12
 *     -> REMOVE ID 419 with key X-65Y21Z12
 *     -> REMOVE ID 421 with key X-65Y21Z12
 *     -> REMOVE ID 422 with key X-65Y21Z12
 *     -> REMOVE ID 423 with key X-65Y21Z12
 *     -> REMOVE ID 424 with key X-65Y21Z12
 * 
 * List Particles -37 = 791
 * 
 * 
 * ---------- Tick Nr 22------------------------------------
 * Collision after X-2Y10Z16         ID   47 with ID   46  Tick    22 
 * Collision after X-2Y10Z16         ID   48 with ID   46  Tick    22 
 * Collision after X-2Y10Z16         ID   49 with ID   46  Tick    22 
 * Collision after X21Y44Z3          ID  376 with ID  375  Tick    22 
 * Collision after X21Y44Z3          ID  378 with ID  375  Tick    22 
 * Collision after X21Y44Z3          ID  379 with ID  375  Tick    22 
 * Collision after X-22Y4Z6          ID  447 with ID  446  Tick    22 
 * Collision after X-22Y4Z6          ID  448 with ID  446  Tick    22 
 * Collision after X-22Y4Z6          ID  450 with ID  446  Tick    22 
 * Collision after X-22Y4Z6          ID  451 with ID  446  Tick    22 
 *     -> REMOVE ID 47 with key X-2Y10Z16
 *     -> REMOVE ID 83 with key X2930Y217Z2458
 *     -> REMOVE ID 48 with key X-2Y10Z16
 *     -> REMOVE ID 49 with key X-2Y10Z16
 *     -> REMOVE ID 376 with key X21Y44Z3
 *     -> REMOVE ID 582 with key X1667Y2944Z3215
 *     -> REMOVE ID 378 with key X21Y44Z3
 *     -> REMOVE ID 379 with key X21Y44Z3
 *     -> REMOVE ID 447 with key X-22Y4Z6
 *     -> REMOVE ID 655 with key X1845Y4887Z1924
 *     -> REMOVE ID 448 with key X-22Y4Z6
 *     -> REMOVE ID 450 with key X-22Y4Z6
 *     -> REMOVE ID 451 with key X-22Y4Z6
 * 
 * List Particles -13 = 778
 * 
 * 
 * ---------- Tick Nr 23------------------------------------
 * Collision after X-17Y20Z4         ID  213 with ID  212  Tick    23 
 * Collision after X-17Y20Z4         ID  214 with ID  212  Tick    23 
 * Collision after X38Y30Z8          ID  304 with ID  303  Tick    23 
 * Collision after X38Y30Z8          ID  305 with ID  303  Tick    23 
 * Collision after X38Y30Z8          ID  306 with ID  303  Tick    23 
 * Collision after X38Y30Z8          ID  308 with ID  303  Tick    23 
 * Collision after X38Y30Z8          ID  309 with ID  303  Tick    23 
 * Collision after X-27Y14Z21        ID  399 with ID  398  Tick    23 
 * Collision after X-27Y14Z21        ID  401 with ID  398  Tick    23 
 * Collision after X-27Y14Z21        ID  402 with ID  398  Tick    23 
 * Collision after X-27Y14Z21        ID  403 with ID  398  Tick    23 
 * Collision after X-27Y14Z21        ID  404 with ID  398  Tick    23 
 *     -> REMOVE ID 213 with key X-17Y20Z4
 *     -> REMOVE ID 371 with key X-1637Y190Z777
 *     -> REMOVE ID 214 with key X-17Y20Z4
 *     -> REMOVE ID 304 with key X38Y30Z8
 *     -> REMOVE ID 516 with key X1216Y2761Z2311
 *     -> REMOVE ID 305 with key X38Y30Z8
 *     -> REMOVE ID 306 with key X38Y30Z8
 *     -> REMOVE ID 308 with key X38Y30Z8
 *     -> REMOVE ID 309 with key X38Y30Z8
 *     -> REMOVE ID 399 with key X-27Y14Z21
 *     -> REMOVE ID 619 with key X-1348Y2138Z5286
 *     -> REMOVE ID 401 with key X-27Y14Z21
 *     -> REMOVE ID 402 with key X-27Y14Z21
 *     -> REMOVE ID 403 with key X-27Y14Z21
 *     -> REMOVE ID 404 with key X-27Y14Z21
 * 
 * List Particles -15 = 763
 * 
 * 
 * ---------- Tick Nr 24------------------------------------
 * Collision after X21Y53Z19         ID   74 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   75 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   76 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   77 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   79 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   80 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   81 with ID   73  Tick    24 
 * Collision after X21Y53Z19         ID   82 with ID   73  Tick    24 
 * Collision after X6Y47Z4           ID   98 with ID   97  Tick    24 
 * Collision after X6Y47Z4           ID   99 with ID   97  Tick    24 
 * Collision after X6Y47Z4           ID  100 with ID   97  Tick    24 
 * Collision after X-8Y7Z11          ID  249 with ID  248  Tick    24 
 * Collision after X-8Y7Z11          ID  250 with ID  248  Tick    24 
 * Collision after X-8Y7Z11          ID  252 with ID  248  Tick    24 
 * Collision after X-8Y7Z11          ID  253 with ID  248  Tick    24 
 * Collision after X-8Y7Z11          ID  254 with ID  248  Tick    24 
 * Collision after X-10Y40Z32        ID  296 with ID  295  Tick    24 
 * Collision after X-10Y40Z32        ID  298 with ID  295  Tick    24 
 * Collision after X-10Y40Z32        ID  299 with ID  295  Tick    24 
 *     -> REMOVE ID 74 with key X21Y53Z19
 *     -> REMOVE ID 136 with key X-1192Y2228Z1031
 *     -> REMOVE ID 75 with key X21Y53Z19
 *     -> REMOVE ID 76 with key X21Y53Z19
 *     -> REMOVE ID 77 with key X21Y53Z19
 *     -> REMOVE ID 79 with key X21Y53Z19
 *     -> REMOVE ID 80 with key X21Y53Z19
 *     -> REMOVE ID 81 with key X21Y53Z19
 *     -> REMOVE ID 82 with key X21Y53Z19
 *     -> REMOVE ID 98 with key X6Y47Z4
 *     -> REMOVE ID 177 with key X-306Y724Z839
 *     -> REMOVE ID 99 with key X6Y47Z4
 *     -> REMOVE ID 100 with key X6Y47Z4
 *     -> REMOVE ID 249 with key X-8Y7Z11
 *     -> REMOVE ID 443 with key X-400Y308Z420
 *     -> REMOVE ID 250 with key X-8Y7Z11
 *     -> REMOVE ID 252 with key X-8Y7Z11
 *     -> REMOVE ID 253 with key X-8Y7Z11
 *     -> REMOVE ID 254 with key X-8Y7Z11
 *     -> REMOVE ID 296 with key X-10Y40Z32
 *     -> REMOVE ID 523 with key X-837Y3255Z2427
 *     -> REMOVE ID 298 with key X-10Y40Z32
 *     -> REMOVE ID 299 with key X-10Y40Z32
 * 
 * List Particles -23 = 740
 * 
 * 
 * ---------- Tick Nr 25------------------------------------
 * Collision after X6Y2Z6            ID  163 with ID  162  Tick    25 
 * Collision after X6Y2Z6            ID  164 with ID  162  Tick    25 
 * Collision after X6Y2Z6            ID  165 with ID  162  Tick    25 
 * Collision after X38Y29Z9          ID  199 with ID  196  Tick    25 
 * Collision after X38Y29Z9          ID  200 with ID  196  Tick    25 
 * Collision after X38Y29Z9          ID  201 with ID  196  Tick    25 
 * Collision after X38Y29Z9          ID  202 with ID  196  Tick    25 
 * Collision after X38Y29Z9          ID  203 with ID  196  Tick    25 
 * Collision after X-16Y30Z2         ID  311 with ID  310  Tick    25 
 * Collision after X-16Y30Z2         ID  312 with ID  310  Tick    25 
 * Collision after X-16Y30Z2         ID  313 with ID  310  Tick    25 
 * Collision after X-16Y30Z2         ID  314 with ID  310  Tick    25 
 * Collision after X-16Y30Z2         ID  315 with ID  310  Tick    25 
 * Collision after X-16Y30Z2         ID  316 with ID  310  Tick    25 
 *     -> REMOVE ID 163 with key X6Y2Z6
 *     -> REMOVE ID 315 with key X-16Y30Z2
 *     -> REMOVE ID 164 with key X6Y2Z6
 *     -> REMOVE ID 165 with key X6Y2Z6
 *     -> REMOVE ID 199 with key X38Y29Z9
 *     -> REMOVE ID 398 with key X-36Y221Z836
 *     -> REMOVE ID 200 with key X38Y29Z9
 *     -> REMOVE ID 201 with key X38Y29Z9
 *     -> REMOVE ID 202 with key X38Y29Z9
 *     -> REMOVE ID 203 with key X38Y29Z9
 *     -> REMOVE ID 311 with key X-16Y30Z2
 *     -> REMOVE ID 563 with key X-1008Y42Z3394
 *     -> REMOVE ID 312 with key X-16Y30Z2
 *     -> REMOVE ID 313 with key X-16Y30Z2
 *     -> REMOVE ID 314 with key X-16Y30Z2
 *     -> REMOVE ID 316 with key X-16Y30Z2
 * 
 * List Particles -16 = 724
 * 
 * 
 * ---------- Tick Nr 26------------------------------------
 * Collision after X-37Y29Z27        ID    1 with ID    0  Tick    26 
 * Collision after X-37Y29Z27        ID    2 with ID    0  Tick    26 
 * Collision after X-37Y29Z27        ID    3 with ID    0  Tick    26 
 * Collision after X-37Y29Z27        ID    4 with ID    0  Tick    26 
 * Collision after X-37Y29Z27        ID    5 with ID    0  Tick    26 
 * Collision after X-37Y29Z27        ID    6 with ID    0  Tick    26 
 * Collision after X-37Y29Z27        ID    7 with ID    0  Tick    26 
 *     -> REMOVE ID 1 with key X-37Y29Z27
 *     -> REMOVE ID 0 with key X-37Y29Z27
 *     -> REMOVE ID 2 with key X-37Y29Z27
 *     -> REMOVE ID 3 with key X-37Y29Z27
 *     -> REMOVE ID 4 with key X-37Y29Z27
 *     -> REMOVE ID 5 with key X-37Y29Z27
 *     -> REMOVE ID 6 with key X-37Y29Z27
 *     -> REMOVE ID 7 with key X-37Y29Z27
 * 
 * List Particles -8 = 716
 * 
 * 
 * ---------- Tick Nr 27------------------------------------
 * Collision after X42Y3Z16          ID  491 with ID  490  Tick    27 
 * Collision after X42Y3Z16          ID  492 with ID  490  Tick    27 
 * Collision after X42Y3Z16          ID  493 with ID  490  Tick    27 
 * Collision after X42Y3Z16          ID  494 with ID  490  Tick    27 
 * Collision after X42Y3Z16          ID  495 with ID  490  Tick    27 
 *     -> REMOVE ID 491 with key X42Y3Z16
 *     -> REMOVE ID 774 with key X733Y2773Z3895
 *     -> REMOVE ID 492 with key X42Y3Z16
 *     -> REMOVE ID 493 with key X42Y3Z16
 *     -> REMOVE ID 494 with key X42Y3Z16
 *     -> REMOVE ID 495 with key X42Y3Z16
 * 
 * List Particles -6 = 710
 * 
 * 
 * ---------- Tick Nr 28------------------------------------
 * Collision after X-33Y13Z30        ID  147 with ID  146  Tick    28 
 * Collision after X-33Y13Z30        ID  148 with ID  146  Tick    28 
 * Collision after X-33Y13Z30        ID  150 with ID  146  Tick    28 
 * Collision after X-33Y13Z30        ID  151 with ID  146  Tick    28 
 * Collision after X-33Y13Z30        ID  152 with ID  146  Tick    28 
 * Collision after X20Y1Z50          ID  290 with ID  289  Tick    28 
 * Collision after X20Y1Z50          ID  291 with ID  289  Tick    28 
 * Collision after X20Y1Z50          ID  292 with ID  289  Tick    28 
 * Collision after X20Y1Z50          ID  293 with ID  289  Tick    28 
 * Collision after X20Y1Z50          ID  294 with ID  289  Tick    28 
 * Collision after X-20Y10Z33        ID  406 with ID  405  Tick    28 
 * Collision after X-20Y10Z33        ID  407 with ID  405  Tick    28 
 * Collision after X21Y51Z31         ID  409 with ID  408  Tick    28 
 * Collision after X21Y51Z31         ID  410 with ID  408  Tick    28 
 * Collision after X21Y51Z31         ID  411 with ID  408  Tick    28 
 * Collision after X21Y51Z31         ID  412 with ID  408  Tick    28 
 * Collision after X21Y51Z31         ID  413 with ID  408  Tick    28 
 * Collision after X21Y51Z31         ID  414 with ID  408  Tick    28 
 * Collision after X21Y51Z31         ID  415 with ID  408  Tick    28 
 *     -> REMOVE ID 147 with key X-33Y13Z30
 *     -> REMOVE ID 322 with key X-1909Y769Z40
 *     -> REMOVE ID 148 with key X-33Y13Z30
 *     -> REMOVE ID 150 with key X-33Y13Z30
 *     -> REMOVE ID 151 with key X-33Y13Z30
 *     -> REMOVE ID 152 with key X-33Y13Z30
 *     -> REMOVE ID 290 with key X20Y1Z50
 *     -> REMOVE ID 572 with key X1212Y1935Z1928
 *     -> REMOVE ID 291 with key X20Y1Z50
 *     -> REMOVE ID 292 with key X20Y1Z50
 *     -> REMOVE ID 293 with key X20Y1Z50
 *     -> REMOVE ID 294 with key X20Y1Z50
 *     -> REMOVE ID 406 with key X-20Y10Z33
 *     -> REMOVE ID 694 with key X4747Y2534Z1548
 *     -> REMOVE ID 407 with key X-20Y10Z33
 *     -> REMOVE ID 409 with key X21Y51Z31
 *     -> REMOVE ID 697 with key X-13Y577Z1768
 *     -> REMOVE ID 410 with key X21Y51Z31
 *     -> REMOVE ID 411 with key X21Y51Z31
 *     -> REMOVE ID 412 with key X21Y51Z31
 *     -> REMOVE ID 413 with key X21Y51Z31
 *     -> REMOVE ID 414 with key X21Y51Z31
 *     -> REMOVE ID 415 with key X21Y51Z31
 * 
 * List Particles -23 = 687
 * 
 * 
 * ---------- Tick Nr 29------------------------------------
 * Collision after X-16Y22Z4         ID  256 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  257 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  258 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  259 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  260 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  261 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  262 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  263 with ID  255  Tick    29 
 * Collision after X-16Y22Z4         ID  264 with ID  255  Tick    29 
 * Collision after X-40Y7Z70         ID  435 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  436 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  437 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  438 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  439 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  440 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  441 with ID  434  Tick    29 
 * Collision after X-40Y7Z70         ID  442 with ID  434  Tick    29 
 *     -> REMOVE ID 256 with key X-16Y22Z4
 *     -> REMOVE ID 556 with key X-1477Y3259Z2843
 *     -> REMOVE ID 257 with key X-16Y22Z4
 *     -> REMOVE ID 258 with key X-16Y22Z4
 *     -> REMOVE ID 259 with key X-16Y22Z4
 *     -> REMOVE ID 260 with key X-16Y22Z4
 *     -> REMOVE ID 261 with key X-16Y22Z4
 *     -> REMOVE ID 262 with key X-16Y22Z4
 *     -> REMOVE ID 263 with key X-16Y22Z4
 *     -> REMOVE ID 264 with key X-16Y22Z4
 *     -> REMOVE ID 435 with key X-40Y7Z70
 *     -> REMOVE ID 746 with key X-2760Y99Z1330
 *     -> REMOVE ID 436 with key X-40Y7Z70
 *     -> REMOVE ID 437 with key X-40Y7Z70
 *     -> REMOVE ID 438 with key X-40Y7Z70
 *     -> REMOVE ID 439 with key X-40Y7Z70
 *     -> REMOVE ID 440 with key X-40Y7Z70
 *     -> REMOVE ID 441 with key X-40Y7Z70
 *     -> REMOVE ID 442 with key X-40Y7Z70
 * 
 * List Particles -19 = 668
 * 
 * 
 * ---------- Tick Nr 30------------------------------------
 * Collision after X-20Y1Z7          ID  139 with ID  138  Tick    30 
 * Collision after X-20Y1Z7          ID  140 with ID  138  Tick    30 
 * Collision after X-20Y1Z7          ID  142 with ID  138  Tick    30 
 * Collision after X-20Y1Z7          ID  144 with ID  138  Tick    30 
 * Collision after X-20Y1Z7          ID  145 with ID  138  Tick    30 
 * Collision after X20Y60Z0          ID  186 with ID  185  Tick    30 
 * Collision after X20Y60Z0          ID  187 with ID  185  Tick    30 
 * Collision after X20Y60Z0          ID  188 with ID  185  Tick    30 
 * Collision after X20Y60Z0          ID  189 with ID  185  Tick    30 
 * Collision after X20Y60Z0          ID  190 with ID  185  Tick    30 
 * Collision after X20Y60Z0          ID  191 with ID  185  Tick    30 
 * Collision after X20Y60Z0          ID  192 with ID  185  Tick    30 
 * Collision after X-41Y22Z35        ID  369 with ID  368  Tick    30 
 * Collision after X-41Y22Z35        ID  370 with ID  368  Tick    30 
 * Collision after X-41Y22Z35        ID  373 with ID  368  Tick    30 
 * Collision after X-41Y22Z35        ID  374 with ID  368  Tick    30 
 *     -> REMOVE ID 139 with key X-20Y1Z7
 *     -> REMOVE ID 367 with key X39Y276Z538
 *     -> REMOVE ID 140 with key X-20Y1Z7
 *     -> REMOVE ID 142 with key X-20Y1Z7
 *     -> REMOVE ID 144 with key X-20Y1Z7
 *     -> REMOVE ID 145 with key X-20Y1Z7
 *     -> REMOVE ID 186 with key X20Y60Z0
 *     -> REMOVE ID 498 with key X736Y1495Z1436
 *     -> REMOVE ID 187 with key X20Y60Z0
 *     -> REMOVE ID 188 with key X20Y60Z0
 *     -> REMOVE ID 189 with key X20Y60Z0
 *     -> REMOVE ID 190 with key X20Y60Z0
 *     -> REMOVE ID 191 with key X20Y60Z0
 *     -> REMOVE ID 192 with key X20Y60Z0
 *     -> REMOVE ID 369 with key X-41Y22Z35
 *     -> REMOVE ID 698 with key X1151Y2938Z3426
 *     -> REMOVE ID 370 with key X-41Y22Z35
 *     -> REMOVE ID 373 with key X-41Y22Z35
 *     -> REMOVE ID 374 with key X-41Y22Z35
 * 
 * List Particles -19 = 649
 * 
 * 
 * ---------- Tick Nr 31------------------------------------
 * Collision after X16Y28Z29         ID  216 with ID  215  Tick    31 
 * Collision after X16Y28Z29         ID  217 with ID  215  Tick    31 
 * Collision after X16Y28Z29         ID  218 with ID  215  Tick    31 
 * Collision after X16Y28Z29         ID  219 with ID  215  Tick    31 
 * Collision after X16Y28Z29         ID  220 with ID  215  Tick    31 
 * Collision after X16Y28Z29         ID  221 with ID  215  Tick    31 
 * Collision after X-17Y22Z22        ID  241 with ID  239  Tick    31 
 * Collision after X-17Y22Z22        ID  242 with ID  239  Tick    31 
 * Collision after X-17Y22Z22        ID  243 with ID  239  Tick    31 
 * Collision after X-17Y22Z22        ID  244 with ID  239  Tick    31 
 * Collision after X-17Y22Z22        ID  245 with ID  239  Tick    31 
 * Collision after X-17Y22Z22        ID  246 with ID  239  Tick    31 
 * Collision after X-17Y22Z22        ID  247 with ID  239  Tick    31 
 *     -> REMOVE ID 216 with key X16Y28Z29
 *     -> REMOVE ID 550 with key X-3225Y1170Z2360
 *     -> REMOVE ID 217 with key X16Y28Z29
 *     -> REMOVE ID 218 with key X16Y28Z29
 *     -> REMOVE ID 219 with key X16Y28Z29
 *     -> REMOVE ID 220 with key X16Y28Z29
 *     -> REMOVE ID 221 with key X16Y28Z29
 *     -> REMOVE ID 241 with key X-17Y22Z22
 *     -> REMOVE ID 579 with key X-692Y155Z1181
 *     -> REMOVE ID 242 with key X-17Y22Z22
 *     -> REMOVE ID 243 with key X-17Y22Z22
 *     -> REMOVE ID 244 with key X-17Y22Z22
 *     -> REMOVE ID 245 with key X-17Y22Z22
 *     -> REMOVE ID 246 with key X-17Y22Z22
 *     -> REMOVE ID 247 with key X-17Y22Z22
 * 
 * List Particles -15 = 634
 * 
 * 
 * ---------- Tick Nr 32------------------------------------
 * Collision after X18Y19Z47         ID   26 with ID   25  Tick    32 
 * Collision after X18Y19Z47         ID   27 with ID   25  Tick    32 
 * Collision after X18Y19Z47         ID   28 with ID   25  Tick    32 
 * Collision after X18Y19Z47         ID   29 with ID   25  Tick    32 
 * Collision after X18Y19Z47         ID   30 with ID   25  Tick    32 
 * Collision after X18Y19Z47         ID   31 with ID   25  Tick    32 
 * Collision after X18Y19Z47         ID   32 with ID   25  Tick    32 
 * Collision after X32Y21Z36         ID   40 with ID   39  Tick    32 
 * Collision after X32Y21Z36         ID   41 with ID   39  Tick    32 
 * Collision after X32Y21Z36         ID   42 with ID   39  Tick    32 
 * Collision after X32Y21Z36         ID   43 with ID   39  Tick    32 
 * Collision after X32Y21Z36         ID   44 with ID   39  Tick    32 
 * Collision after X32Y21Z36         ID   45 with ID   39  Tick    32 
 * Collision after X-61Y58Z8         ID   94 with ID   93  Tick    32 
 * Collision after X-61Y58Z8         ID   95 with ID   93  Tick    32 
 * Collision after X-61Y58Z8         ID   96 with ID   93  Tick    32 
 * Collision after X39Y19Z23         ID  102 with ID  101  Tick    32 
 * Collision after X39Y19Z23         ID  103 with ID  101  Tick    32 
 * Collision after X39Y19Z23         ID  104 with ID  101  Tick    32 
 * Collision after X39Y19Z23         ID  105 with ID  101  Tick    32 
 * Collision after X39Y19Z23         ID  106 with ID  101  Tick    32 
 * Collision after X39Y19Z23         ID  107 with ID  101  Tick    32 
 * Collision after X15Y52Z27         ID  270 with ID  269  Tick    32 
 * Collision after X15Y52Z27         ID  271 with ID  269  Tick    32 
 * Collision after X15Y52Z27         ID  272 with ID  269  Tick    32 
 * Collision after X15Y52Z27         ID  273 with ID  269  Tick    32 
 * Collision after X15Y52Z27         ID  274 with ID  269  Tick    32 
 * Collision after X15Y52Z27         ID  275 with ID  269  Tick    32 
 * Collision after X-22Y21Z38        ID  362 with ID  361  Tick    32 
 * Collision after X-22Y21Z38        ID  363 with ID  361  Tick    32 
 * Collision after X-22Y21Z38        ID  364 with ID  361  Tick    32 
 * Collision after X-22Y21Z38        ID  365 with ID  361  Tick    32 
 * Collision after X-22Y21Z38        ID  366 with ID  361  Tick    32 
 * Collision after X-27Y22Z12        ID  395 with ID  394  Tick    32 
 * Collision after X-27Y22Z12        ID  396 with ID  394  Tick    32 
 * Collision after X-27Y22Z12        ID  397 with ID  394  Tick    32 
 *     -> REMOVE ID 26 with key X18Y19Z47
 *     -> REMOVE ID 56 with key X-1680Y40Z1057
 *     -> REMOVE ID 27 with key X18Y19Z47
 *     -> REMOVE ID 28 with key X18Y19Z47
 *     -> REMOVE ID 29 with key X18Y19Z47
 *     -> REMOVE ID 30 with key X18Y19Z47
 *     -> REMOVE ID 31 with key X18Y19Z47
 *     -> REMOVE ID 32 with key X18Y19Z47
 *     -> REMOVE ID 40 with key X32Y21Z36
 *     -> REMOVE ID 96 with key X-61Y58Z8
 *     -> REMOVE ID 41 with key X32Y21Z36
 *     -> REMOVE ID 42 with key X32Y21Z36
 *     -> REMOVE ID 43 with key X32Y21Z36
 *     -> REMOVE ID 44 with key X32Y21Z36
 *     -> REMOVE ID 45 with key X32Y21Z36
 *     -> REMOVE ID 94 with key X-61Y58Z8
 *     -> REMOVE ID 289 with key X-1958Y689Z1966
 *     -> REMOVE ID 95 with key X-61Y58Z8
 *     -> REMOVE ID 102 with key X39Y19Z23
 *     -> REMOVE ID 321 with key X-222Y1610Z2222
 *     -> REMOVE ID 103 with key X39Y19Z23
 *     -> REMOVE ID 104 with key X39Y19Z23
 *     -> REMOVE ID 105 with key X39Y19Z23
 *     -> REMOVE ID 106 with key X39Y19Z23
 *     -> REMOVE ID 107 with key X39Y19Z23
 *     -> REMOVE ID 270 with key X15Y52Z27
 *     -> REMOVE ID 629 with key X-77Y38Z2265
 *     -> REMOVE ID 271 with key X15Y52Z27
 *     -> REMOVE ID 272 with key X15Y52Z27
 *     -> REMOVE ID 273 with key X15Y52Z27
 *     -> REMOVE ID 274 with key X15Y52Z27
 *     -> REMOVE ID 275 with key X15Y52Z27
 *     -> REMOVE ID 362 with key X-22Y21Z38
 *     -> REMOVE ID 725 with key X537Y3414Z1394
 *     -> REMOVE ID 363 with key X-22Y21Z38
 *     -> REMOVE ID 364 with key X-22Y21Z38
 *     -> REMOVE ID 365 with key X-22Y21Z38
 *     -> REMOVE ID 366 with key X-22Y21Z38
 *     -> REMOVE ID 395 with key X-27Y22Z12
 *     -> REMOVE ID 759 with key X3556Y1751Z1570
 *     -> REMOVE ID 396 with key X-27Y22Z12
 *     -> REMOVE ID 397 with key X-27Y22Z12
 * 
 * List Particles -42 = 592
 * 
 * 
 * ---------- Tick Nr 33------------------------------------
 * Collision after X23Y10Z8          ID  426 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  427 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  428 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  429 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  430 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  431 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  432 with ID  425  Tick    33 
 * Collision after X23Y10Z8          ID  433 with ID  425  Tick    33 
 *     -> REMOVE ID 426 with key X23Y10Z8
 *     -> REMOVE ID 833 with key X-410Y1152Z489
 *     -> REMOVE ID 427 with key X23Y10Z8
 *     -> REMOVE ID 428 with key X23Y10Z8
 *     -> REMOVE ID 429 with key X23Y10Z8
 *     -> REMOVE ID 430 with key X23Y10Z8
 *     -> REMOVE ID 431 with key X23Y10Z8
 *     -> REMOVE ID 432 with key X23Y10Z8
 *     -> REMOVE ID 433 with key X23Y10Z8
 * 
 * List Particles -9 = 583
 * 
 * 
 * ---------- Tick Nr 34------------------------------------
 * Collision after X-47Y0Z28         ID  173 with ID  172  Tick    34 
 * Collision after X-47Y0Z28         ID  174 with ID  172  Tick    34 
 * Collision after X-47Y0Z28         ID  175 with ID  172  Tick    34 
 * Collision after X-47Y0Z28         ID  176 with ID  172  Tick    34 
 * Collision after X-28Y20Z30        ID  482 with ID  481  Tick    34 
 * Collision after X-28Y20Z30        ID  483 with ID  481  Tick    34 
 * Collision after X-28Y20Z30        ID  484 with ID  481  Tick    34 
 * Collision after X-28Y20Z30        ID  485 with ID  481  Tick    34 
 * Collision after X-28Y20Z30        ID  486 with ID  481  Tick    34 
 *     -> REMOVE ID 173 with key X-47Y0Z28
 *     -> REMOVE ID 573 with key X-1327Y506Z75
 *     -> REMOVE ID 174 with key X-47Y0Z28
 *     -> REMOVE ID 175 with key X-47Y0Z28
 *     -> REMOVE ID 176 with key X-47Y0Z28
 *     -> REMOVE ID 482 with key X-28Y20Z30
 *     -> REMOVE ID 898 with key X-2320Y278Z2231
 *     -> REMOVE ID 483 with key X-28Y20Z30
 *     -> REMOVE ID 484 with key X-28Y20Z30
 *     -> REMOVE ID 485 with key X-28Y20Z30
 *     -> REMOVE ID 486 with key X-28Y20Z30
 * 
 * List Particles -11 = 572
 * 
 * 
 * ---------- Tick Nr 35------------------------------------
 * Collision after X30Y41Z28         ID  381 with ID  380  Tick    35 
 * Collision after X30Y41Z28         ID  382 with ID  380  Tick    35 
 * Collision after X30Y41Z28         ID  383 with ID  380  Tick    35 
 *     -> REMOVE ID 381 with key X30Y41Z28
 *     -> REMOVE ID 806 with key X6486Y420Z603
 *     -> REMOVE ID 382 with key X30Y41Z28
 *     -> REMOVE ID 383 with key X30Y41Z28
 * 
 * List Particles -4 = 568
 * 
 * 
 * ---------- Tick Nr 36------------------------------------
 * Collision after X-16Y15Z18        ID  120 with ID  119  Tick    36 
 *     -> REMOVE ID 120 with key X-16Y15Z18
 *     -> REMOVE ID 525 with key X3683Y631Z326
 * 
 * List Particles -2 = 566
 * 
 * 
 * ---------- Tick Nr 37------------------------------------
 * Collision after X9Y14Z10          ID   14 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   15 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   16 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   17 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   18 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   19 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   20 with ID   13  Tick    37 
 * Collision after X9Y14Z10          ID   21 with ID   13  Tick    37 
 * Collision after X-5Y48Z7          ID  224 with ID  222  Tick    37 
 * Collision after X-5Y48Z7          ID  225 with ID  222  Tick    37 
 * Collision after X-5Y48Z7          ID  226 with ID  222  Tick    37 
 * Collision after X-5Y48Z7          ID  227 with ID  222  Tick    37 
 * Collision after X-5Y48Z7          ID  228 with ID  222  Tick    37 
 * Collision after X-5Y48Z7          ID  229 with ID  222  Tick    37 
 *     -> REMOVE ID 14 with key X9Y14Z10
 *     -> REMOVE ID 84 with key X174Y630Z79
 *     -> REMOVE ID 15 with key X9Y14Z10
 *     -> REMOVE ID 16 with key X9Y14Z10
 *     -> REMOVE ID 17 with key X9Y14Z10
 *     -> REMOVE ID 18 with key X9Y14Z10
 *     -> REMOVE ID 19 with key X9Y14Z10
 *     -> REMOVE ID 20 with key X9Y14Z10
 *     -> REMOVE ID 21 with key X9Y14Z10
 *     -> REMOVE ID 224 with key X-5Y48Z7
 *     -> REMOVE ID 645 with key X2014Y4029Z3737
 *     -> REMOVE ID 225 with key X-5Y48Z7
 *     -> REMOVE ID 226 with key X-5Y48Z7
 *     -> REMOVE ID 227 with key X-5Y48Z7
 *     -> REMOVE ID 228 with key X-5Y48Z7
 *     -> REMOVE ID 229 with key X-5Y48Z7
 * 
 * List Particles -16 = 550
 * 
 * 
 * ---------- Tick Nr 38------------------------------------
 * Collision after X58Y23Z18         ID   86 with ID   85  Tick    38 
 * Collision after X58Y23Z18         ID   87 with ID   85  Tick    38 
 * Collision after X58Y23Z18         ID   88 with ID   85  Tick    38 
 * Collision after X58Y23Z18         ID   89 with ID   85  Tick    38 
 * Collision after X58Y23Z18         ID   90 with ID   85  Tick    38 
 * Collision after X58Y23Z18         ID   91 with ID   85  Tick    38 
 * Collision after X58Y23Z18         ID   92 with ID   85  Tick    38 
 * Collision after X6Y1Z5            ID  319 with ID  318  Tick    38 
 * Collision after X6Y1Z5            ID  320 with ID  318  Tick    38 
 * Collision after X6Y1Z5            ID  323 with ID  318  Tick    38 
 * Collision after X6Y1Z5            ID  324 with ID  318  Tick    38 
 * Collision after X6Y1Z5            ID  325 with ID  318  Tick    38 
 * Collision after X-33Y82Z43        ID  445 with ID  444  Tick    38 
 *     -> REMOVE ID 86 with key X58Y23Z18
 *     -> REMOVE ID 504 with key X559Y398Z2673
 *     -> REMOVE ID 87 with key X58Y23Z18
 *     -> REMOVE ID 88 with key X58Y23Z18
 *     -> REMOVE ID 89 with key X58Y23Z18
 *     -> REMOVE ID 90 with key X58Y23Z18
 *     -> REMOVE ID 91 with key X58Y23Z18
 *     -> REMOVE ID 92 with key X58Y23Z18
 *     -> REMOVE ID 319 with key X6Y1Z5
 *     -> REMOVE ID 764 with key X-999Y1340Z1351
 *     -> REMOVE ID 320 with key X6Y1Z5
 *     -> REMOVE ID 323 with key X6Y1Z5
 *     -> REMOVE ID 324 with key X6Y1Z5
 *     -> REMOVE ID 325 with key X6Y1Z5
 *     -> REMOVE ID 445 with key X-33Y82Z43
 *     -> REMOVE ID 893 with key X1217Y882Z32
 * 
 * List Particles -16 = 534
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 308
 * Result Part 1 534
 * 
 */