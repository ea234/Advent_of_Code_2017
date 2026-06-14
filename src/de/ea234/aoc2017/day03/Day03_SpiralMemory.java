package de.ea234.aoc2017.day03;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * --- Day 3: Spiral Memory ---
 * https://adventofcode.com/2017/day/3
 * 
 * https://www.reddit.com/r/adventofcode/comments/7h7ufl/2017_day_3_solutions/
 * 
 * 
 *  577 576 575 574 573 572 571 570 569 568 567 566 565 564 563 562 561 560 559 558 557 556 555
 *  578 485 484 483 482 481 480 479 478 477 476 475 474 473 472 471 470 469 468 467 466 465 464
 *  579 486 401 400 399 398 397 396 395 394 393 392 391 390 389 388 387 386 385 384 383 382 381
 *  580 487 402 325 324 323 322 321 320 319 318 317 316 315 314 313 312 311 310 309 308 307 380
 *  581 488 403 326 257 256 255 254 253 252 251 250 249 248 247 246 245 244 243 242 241 306 379
 *  582 489 404 327 258 197 196 195 194 193 192 191 190 189 188 187 186 185 184 183 240 305 378
 *  583 490 405 328 259 198 145 144 143 142 141 140 139 138 137 136 135 134 133 182 239 304 377
 *  584 491 406 329 260 199 146 101 100  99  98  97  96  95  94  93  92  91 132 181 238 303 376
 *  585 492 407 330 261 200 147 102  65  64  63  62  61  60  59  58  57  90 131 180 237 302 375
 *  586 493 408 331 262 201 148 103  66  37  36  35  34  33  32  31  56  89 130 179 236 301 374
 *  587 494 409 332 263 202 149 104  67  38  17  16  15  14  13  30  55  88 129 178 235 300 373
 *  588 495 410 333 264 203 150 105  68  39  18   5   4   3  12  29  54  87 128 177 234 299 372
 *  589 496 411 334 265 204 151 106  69  40  19   6   1   2  11  28  53  86 127 176 233 298 371
 *  590 497 412 335 266 205 152 107  70  41  20   7   8   9  10  27  52  85 126 175 232 297 370
 *  591 498 413 336 267 206 153 108  71  42  21  22  23  24  25  26  51  84 125 174 231 296 369
 *  592 499 414 337 268 207 154 109  72  43  44  45  46  47  48  49  50  83 124 173 230 295 368
 *  593 500 415 338 269 208 155 110  73  74  75  76  77  78  79  80  81  82 123 172 229 294 367
 *  594 501 416 339 270 209 156 111 112 113 114 115 116 117 118 119 120 121 122 171 228 293 366
 *  595 502 417 340 271 210 157 158 159 160 161 162 163 164 165 166 167 168 169 170 227 292 365
 *  596 503 418 341 272 211 212 213 214 215 216 217 218 219 220 221 222 223 224 225 226 291 364
 *  597 504 419 342 273 274 275 276 277 278 279 280 281 282 283 284 285 286 287 288 289 290 363
 *  598 505 420 343 344 345 346 347 348 349 350 351 352 353 354 355 356 357 358 359 360 361 362
 *  599 506 421 422 423 424 425 426 427 428 429 430 431 432 433 434 435 436 437 438 439 440 441
 *  600 507 508 509 510 511 512 513 514 515 516 517 518 519 520 521 522 523 524 525 526 527 528
 * 
 * 
 *       .      .      .      .      .      .      .      .      .      .
 *       . 363010 349975 330785 312453 295229 279138 266330 130654      .
 *       .   6591   6444   6155   5733   5336   5022   2450 128204      .
 *       .  13486    147    142    133    122     59   2391 123363      .
 *       .  14267    304      5      4      2     57   2275 116247      .
 *       .  15252    330     10      1      1     54   2105 109476      .
 *       .  16295    351     11     23     25     26   1968 103128      .
 *       .  17008    362    747    806    880    931    957  98098      .
 *       .  17370  35487  37402  39835  42452  45220  47108  48065      .
 *       .      .      .      .      .      .      .      .      .      .
 * 
 * 
 * round_nr      300
 * 
 * old_value     361202
 * cur_value     361803
 * value_x       361527
 * 
 * cur_coords_vx R-24C302
 * cur_coords_v1 R1C1
 * 
 * Value X   Row =   -24 , Column =   302 
 * Value 1   Row =     1 , Column =     1 
 *           Row =    25 , Column =   301 
 * 
 * Result Part 1 326
 * Result Part 2 363010
 * 
 * </pre>
 */
public class Day03_SpiralMemory
{
  private static final Pattern PATTERN              = Pattern.compile( "^R(-?\\d+)C(-?\\d+)$" );

  private static final String  PREFIX_VALUE_PART_1  = "p1_value_";

  private static final String  PREFIX_COORDS_PART_1 = "p1_coords_";

  private static final String  PREFIX_COORDS_PART_2 = "adj_coords_";

  private static final long    VALUE_TO_SEARCH      = 361527;

  private static long          result_part_02       = 0;

  public static void main( String[] args )
  {
    calculate01();

    System.exit( 0 );
  }

  private static void calculate01()
  {
    long result_part_01 = 0;

    long round_nr       = 0;

    long old_value      = 0;
    long cur_value      = 1;

    long value_add      = 0;

    long grid_left      = 1;
    long grid_right     = 1;
    long grid_top       = 1;
    long grid_bottom    = 1;

    Properties prop_mem = new Properties();

    String cur_coords = "R" + grid_top + "C" + grid_left;

    prop_mem.setProperty( PREFIX_VALUE_PART_1  + cur_value,  cur_coords );
    prop_mem.setProperty( PREFIX_COORDS_PART_1 + cur_coords, "" + cur_value );

    prop_mem.setProperty( PREFIX_COORDS_PART_2 + cur_coords, "" + cur_value );

    while ( ( round_nr < 1000 ) && ( cur_value < 361527 ) )
    {
      /*
       * Expand Right
       */

      old_value = cur_value;

      grid_right++;

      value_add = grid_right - grid_left;

      cur_value = addToCol( prop_mem, cur_value, grid_bottom, grid_left, 1, value_add );

      if ( ( old_value <= VALUE_TO_SEARCH ) && ( cur_value >= VALUE_TO_SEARCH ) )
      {
        break;
      }

      /*
       * Expand Top
       */

      old_value = cur_value;

      grid_top--;

      value_add = grid_bottom - grid_top;

      cur_value = addToRow( prop_mem, cur_value, grid_bottom, grid_right, -1, value_add );

      if ( ( old_value <= VALUE_TO_SEARCH ) && ( cur_value >= VALUE_TO_SEARCH ) )
      {
        break;
      }

      /*
       * Expand Left
       */

      old_value = cur_value;

      grid_left--;

      value_add = grid_right - grid_left;

      cur_value = addToCol( prop_mem, cur_value, grid_top, grid_right, -1, value_add );

      if ( ( old_value <= VALUE_TO_SEARCH ) && ( cur_value >= VALUE_TO_SEARCH ) )
      {
        break;
      }

      /*
       * Expand down
       */

      old_value = cur_value;

      grid_bottom++;

      value_add = grid_bottom - grid_top;

      cur_value = addToRow( prop_mem, cur_value, grid_top, grid_left, 1, value_add );

      if ( ( old_value <= VALUE_TO_SEARCH ) && ( cur_value >= VALUE_TO_SEARCH ) )
      {
        break;
      }

      round_nr++;
    }

    String cur_coords_vx = prop_mem.getProperty( PREFIX_VALUE_PART_1 + VALUE_TO_SEARCH );
    String cur_coords_v1 = prop_mem.getProperty( PREFIX_VALUE_PART_1 + 1               );

    Result coords_value_x = parse( cur_coords_vx );
    Result coords_value_1 = parse( cur_coords_v1 );

    result_part_01 = Math.abs( coords_value_x.row - coords_value_1.row ) + Math.abs( coords_value_x.column - coords_value_1.column );

    wl( "" );
    wl( getDebugGrid( PREFIX_COORDS_PART_1, " %3s", prop_mem, -11, -11, 13, 12 ) );
    wl( "" );
    wl( getDebugGrid( PREFIX_COORDS_PART_2, " %6s", prop_mem,  -4,  -3,  6,  7 ) );
    wl( "" );
    wl( "round_nr      " + round_nr );
    wl( "" );
    wl( "old_value     " + old_value );
    wl( "cur_value     " + cur_value );
    wl( "value_x       " + VALUE_TO_SEARCH );
    wl( "" );
    wl( "cur_coords_vx " + cur_coords_vx );
    wl( "cur_coords_v1 " + cur_coords_v1 );
    wl( "" );
    wl( String.format( "Value X   Row = %5d , Column = %5d ", coords_value_x.row, coords_value_x.column ) );
    wl( String.format( "Value 1   Row = %5d , Column = %5d ", coords_value_1.row, coords_value_1.column ) );
    wl( String.format( "          Row = %5d , Column = %5d ", Math.abs( coords_value_x.row - coords_value_1.row ), Math.abs( coords_value_x.column - coords_value_1.column ) ) );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String getDebugGrid( String pPrefix, String pFormat, Properties pProperties, long pFromRow, long pFromCol, long pToRow, long pToCol )
  {
    String result_key = "";

    for ( int cur_row = (int) pFromRow; cur_row < pToRow; cur_row++ )
    {
      for ( int cur_col = (int) pFromCol; cur_col < pToCol; cur_col++ )
      {
        result_key += String.format( pFormat, pProperties.getProperty( pPrefix + "R" + cur_row + "C" + cur_col, "." ) );
      }

      result_key += "\n";
    }

    return result_key;
  }

  private static long addToCol( Properties pProperties, long pCurValue, long pRow, long pCol, long pDeltaCol, long pDeltaCount )
  {
    for ( int step_count = 0; step_count < pDeltaCount; step_count++ )
    {
      pCol += pDeltaCol;

      pCurValue++;

      String cur_coords = "R" + pRow + "C" + pCol;

      pProperties.setProperty( PREFIX_VALUE_PART_1  + pCurValue,  cur_coords );
      pProperties.setProperty( PREFIX_COORDS_PART_1 + cur_coords, "" + pCurValue );

      if ( result_part_02 == 0 )
      {
        long adjacent_cell_values = getAdjacentValues( pProperties, pRow, pCol );

        pProperties.setProperty( PREFIX_COORDS_PART_2 + cur_coords, "" + adjacent_cell_values );
      }
    }

    return pCurValue;
  }

  private static long addToRow( Properties pProperties, long pCurValue, long pRow, long pCol, long pDeltaRow, long pDeltaCount )
  {
    for ( int step_count = 0; step_count < pDeltaCount; step_count++ )
    {
      pRow += pDeltaRow;

      pCurValue++;

      String cur_coords = "R" + pRow + "C" + pCol;

      pProperties.setProperty( PREFIX_VALUE_PART_1  + pCurValue, cur_coords );
      pProperties.setProperty( PREFIX_COORDS_PART_1 + cur_coords, "" + pCurValue );

      if ( result_part_02 == 0 )
      {
        long adjacent_cell_values = getAdjacentValues( pProperties, pRow, pCol );

        pProperties.setProperty( PREFIX_COORDS_PART_2 + cur_coords, "" + adjacent_cell_values );
      }
    }

    return pCurValue;
  }

  private static long getAdjacentValues( Properties pProperties, long pRow, long pCol )
  {
    /*
     * https://github.com/ea234/Advent_of_Code_2020/blob/main/src/day_11__Seating_System.ts
     */

    long adjacent_cell_values = 0;

    adjacent_cell_values += getValue( pProperties, "R" + pRow + "C" + ( pCol - 1 ) );
    adjacent_cell_values += getValue( pProperties, "R" + pRow + "C" + ( pCol + 1 ) );

    adjacent_cell_values += getValue( pProperties, "R" + ( pRow - 1 ) + "C" + ( pCol - 1 ) );
    adjacent_cell_values += getValue( pProperties, "R" + ( pRow - 1 ) + "C" +   pCol       );
    adjacent_cell_values += getValue( pProperties, "R" + ( pRow - 1 ) + "C" + ( pCol + 1 ) );

    adjacent_cell_values += getValue( pProperties, "R" + ( pRow + 1 ) + "C" + ( pCol - 1 ) );
    adjacent_cell_values += getValue( pProperties, "R" + ( pRow + 1 ) + "C" +   pCol       );
    adjacent_cell_values += getValue( pProperties, "R" + ( pRow + 1 ) + "C" + ( pCol + 1 ) );

    if ( adjacent_cell_values > VALUE_TO_SEARCH )
    {
      result_part_02 = adjacent_cell_values;
    }

    return adjacent_cell_values;
  }

  private static long getValue( Properties pProp, String pCoords )
  {
    String value = pProp.getProperty( PREFIX_COORDS_PART_2 + pCoords, "0" );

    return Long.parseLong( value );
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

  private static Result parse( String pInput )
  {
    if ( pInput == null )
    {
      throw new IllegalArgumentException( "input darf nicht null sein" );
    }

    Matcher m = PATTERN.matcher( pInput.trim() );

    if ( !m.matches() )
    {
      throw new IllegalArgumentException( "Ungültiges Format. Erwartet: R<Zahl>C<Zahl>, z.B. R12C33 oder R-5C-7" );
    }

    long row = Long.parseLong( m.group( 1 ) );
    long col = Long.parseLong( m.group( 2 ) );

    return new Result( row, col );
  }

  private static class Result
  {
    public final long row;

    public final long column;

    public Result( long row, long column )
    {
      this.row = row;
      this.column = column;
    }
  }
}
