# Panel creator
A small utility to generate front panels for your DIY (which can also be used as drilling templates). It is targetting small-medium panel sizes as the panel must fit in an A4 paper.

### Quick description
You have to describe your panel in terms of primitive items (eg lines, boxes etc) and high level items (switches, pots, dials etc) placed in the proper place using X,Y coordinates and wherever needed specific attributes like _diameter_, _text_ etc. The description is in JSON format (you will need to be a bit familiar with the syntax) and the file is given to the application. The output is a pdf file you can print. 

### Some notes
* You need to have Java 8 or later to run the application.
* You need to be comfortable to run an application from the command line.
* You need to be comfortable to work on a non WYSIWYG style, with no previews and live rendering.
* It can be helpful if you can draw the panel on a 1mm squared graph paper like this [http://www.greatlittleminds.com/pdfs/graph-paper-to-print/graph-paper-1mm-sq.pdf] in advance. That will make extraction of the correct X,Y coordinates easier.
* Familiarise yourself with a bit of JSON notation and structure. See [A quick introduction to JSON] and [An Introduction to JSON - Syntax and structure]

### How to create a template
Create a text file and give it any name you like. Filename or extension makes no difference so use something that makes sense to you. Use an editor that can save the file as plain text (TXT) and not a word processor like Word , LibreOffice etc. Your file must be pure text format. Put in the file the description of the controls you want. Save it and then run the application giving your file as input. 

### Supported controls and their syntax and properties
The application supports the following controls:
* Line - creates a straight line of a specified length.
* Label - creates a simple label that contains text in a single line.
* Rectangle - creates a rectangular box of specified width and height (todo).
* Circle - creates a simple circle of the appropriate diameter.
* Dial - creates a dial for a round potentiometer with scale, numbers and ticks.

### Controls syntax and properties
Every control has the following basic properties.
* _name_ - An optional name for the control for easy reference in the design (not to be confused with the text that appears in a label). When you have quite a lot of controls, this is handy to quickly find the one you need!
* _type_ - defines the type of the item/control (see items' description for possible values).
* _x_ - the X coordinate of the control as measured from the left-bottom corner.
* _y_ - the Y coordinate of the control as measured from the left-bottom corner.

The coordinate system assumes the (0,0) point to be the left-bottom corner of the page and depending on the specific control it represents a slightly different thing (see in the description of each control).

##### Panel
This is not actually a control but represents the whole panel and defines its basic properties like width, height, border etc. This is the _only_ item that does not need _x_ and _y_ coordinates as it will always start from the (0,0) point and be as much high or wide you define it. It also does not need a _type_ property as it is implied.

**This _MUST_ be the top level item in your file**

here is an example, 
```
{
  "width": 100,
  "height": 70,
  "border": true,
  "padding": 0,
  "line": 0.2,
  "units": "mm",
  "items": [
        ...
  ]
 } 
```
the top level item supports the following properties:
* _units_ - it can be either "mm" (for millimeters) or "in" (for inches). This property sets how the units are interpreted through the *whole* file. Wherever a diameter or length or width or height is specified, that number is assumed to be in this unit of measurement.
* _width_ - the width of your panel in the specific units.
* _height_ - the height of your panel in the specific units.
* _border_ - set this to _true_ if you want the panel to have a border or _false_ otherwise.
* _line_ - the width of the border line (if border is visible).
* _padding_ - this is the distance of the border from the panel edges. it does just that and it does not move your design from the origin (0,0). It is purely for aesthetic reasons if you want the border to not touch the edge of the panel.
* _items_ - this is the collection of the panel items. Here you can add everything your panel should have.

##### Line
This is a simple line that can be either horizontal or vertical (no arbitrary angles are supported at the moment). 

here is an example, 
```
{
  "x": 85,
  "y": 65,
  "type": "line",
  "orientation": "horizontal",
  "length": 15,
  "line": 0.3
}
```
the line supports the following properties:
* _type_ - this property must have the value of "line".
* _x_ - this represents the X coordinate of the start point of the line.
* _y_ - this represents the Y coordinate of the start point of the line.
* _orientation_ - it can be either "horizontal" or "vertical" to create a line of the respective orientation.
* _length_ - the length of the line. **NOTE:** horizontal lines extend to the right of the starting point, while vertical lines extend upwards of the starting point.
* _line_ - despite its name, this represents the width of the line.


##### Label
This is a simple text label that can display a single line of text (multiline text is not supported). 

here is an example, 
```
{
  "name": "Input connector",
  "x": 40,
  "y": 90,
  "type": "label",
  "text": "Input",
  "size": 11,
  "font": 2,
  "bold": true,
  "center": true
}
```
the label supports the following properties:
* _type_ - this property must have the value of "label".
* _x_ - this represents the X coordinate of the start point of the text.
* _y_ - this represents the Y coordinate of the start point of the text.
* _text_ - the text content of the label (this is what is displayed).
* _size_ - this is the font size (in points) that is used for the text.
* _font_ - this is the index of the [internal fonts](#internal-fonts) to use for the text.
* _bold_ - set this to _true_ to make the text **bold** or _false_ to use the regular font (this is the default is not specified).
* _center_ - if set to _true_ the text of the label is center aligned on (x,y) 

##### Circle
This is a circle of a specific diameter with an outline and an optional drill mark (cross-hair). 

here is an example, 
```
{
  "x": 40,
  "y": 30,
  "type": "circle",
  "line": 0.4,
  "dia": 6,
  "drillmark": true
}
```
the circle supports the following properties:
* _type_ - this property must have the value of "circle"
* _x_ - this represents the X coordinate of the centre of the circle.
* _y_ - this represents the Y coordinate of the centre of the circle.
* _dia_ - this is the diameter of the circle.
* _drillmark_ - specifies whether a cross-hair mark should be drawn inside the circle as a drilling aid. It can be either _true_ or _false_ and if not specified it default to _false_ (no drill-mark).
* _line_ - despite its name, this represents the width of the line used on the circle outline.


##### Dial
This is one of the most complex controls as it generates a place for a round control (e.g potentiometer) along with its dial and scale. The scale is very customisable which makes the control a bit lengthy to describe, but the result definitely worths the time spent! **NOTE:** A dial control does not include the hole for the shaft, it is just the dial around it. A separate whole is required in the panel description file. 

here is an example, 
```
{
  "name": "Volume dial",
  "x": 100,
  "y": 30,
  "type": "dial",
  "dia": 35,
  "extend": 240,
  "anchor": 180,
  "baseline": true,
  "line": 0.4,
  "scale": {
    ... all the configuration for the scale goes here, see the description
  }
}
```
the dial supports the following properties:
* _type_ - this property must have the value of "dial".
* _x_ - this represents the X coordinate of the centre of the dial.
* _y_ - this represents the Y coordinate of the centre of the dial.
* _dia_ - this is the diameter of the beginning of the dial. This is the baseline that starts outside the knob you are planning to use.
* _baseline_ - specifies whether a solid circular line will be drawn following the baseline of the dial (that will be exactly where the knob ends).It can be either _true_ or _false_ and if not specified it default to _false_ (no line).
* _line_ - despite its name, this represents the width of the line used for drawing the baseline.
* _anchor_ - this is the point of the circle where the _middle_ of the unoccupied dial segment should rest. This a clock-wise angle ranging from 0 to 360 and the reference point is the top (12'clock). This helps to rotate the dial around the center and center the range of the dial opposite to it.
* _extend_ - this is the range of the dial (in degrees) and depends on the actual control you are using. For example, a typical single turn potentiometer has a range of 270 degrees and a typical variable capacitor a range of 180 degrees.
* _scale_ - this is the most useful but at the same time optional part of the dial. It describes how the dial looks like (numbers, lines, scale etc) and if not provided nothing is used.


##### Scale
This is part of the dial and cannot appear by itself, it has to be inside a dial control. The description of this control allows all the common scales to be created, from linear scales that devide equally a specific range, to custom markers in arbitrary points around the dial.

A dial scale comes in 2 variants:
* one that is made of a min-max with equal divisions (and subdivisions)
* one that is made of custom marks placed at specific angles

###### Min-Max scales
Since the scale can become too complex, here is a series of examples , starting from the simplest one,

```
"scale": {
    "min": 0,
    "max": 10,
    "ticks": [
      {
        "values": true,
        "decimals": 1,
        "size": 12,
        "font": 1,
        "bold": true,
        "length": 3,
        "width": 0.5,
        "step": 1,
        "labeldia": 52
      }
    ]
}
```

this represents a scale with a min-max (for example one that could be used in a volume control). 
this type of dial supports the following properties:
* _min_ - the minimum value of the scale. Can be any number, positive or negative. It has to be smaller than the value of _max_.
* _max_ - the maximum value of the scale. Can be any number, positive or negative. It has to be larger than the value of _min_.
* _ticks_ - specifies the ticks (marks) of the scale. Note that this is a collection, as multiple ticks can be overlaid to create divisions and subdivisions of the scale (see next example).

the _ticks_ defines how the range between min and max is _equally_ divided in a specified number of steps and how the ticks are arranged.

Continuing the above example, the _ticks_ spcify that the range 0..10 will be marked in steps of 1 unit using 1 decimal point on the text.

```
"scale": {
    ...
    "ticks": [
      {
        "values": true,
        "decimals": 1,
        "size": 12,
        "font": 1,
        "bold": true,
        "length": 3,
        "width": 0.5,
        "step": 1,
        "labeldia": 52
      }
    ]
}
```
in detail, the ticks support the following properties:
* _values_ - set to _true_ to display the values over each mark, or _false_ to just show the mark.
* _decimals_ - sets the number of digits after the decimal point to display on the text of each marker.
* _length_ - this is the length of each marker line.
* _width_ - this is the width of each marker line.
* _step_ - the increment (or difference) in value between two markers.
* _labeldia_ - this is the diameter of the imaginary circle where the scale values are placed (defines how far away from the markers the values are placed).
* _size_ - this is the font size (in points) that is used for the text
* _font_ - this is the index of the [internal font] to use for the text
* _bold_ - set this to _true_ to make the text **bold** or _false_ to use the regular font (this is the default is not specified).

Since ticks is a collection, it means that we can add a secondary scale to show subdivisions of the main scale. This is done by simply adding a second group with the minimal info. The example below extends the previous one by adding a scale with a step of 0.2 which divides the main scale in 5 equal marks, using shorter marks.

```
"scale": {
    "min": 0,
    "max": 10,
    "ticks": [
      {
        "values": true,
        "decimals": 1,
        "size": 12,
        "font": 1,
        "bold": true,
        "length": 3,
        "width": 0.5,
        "step": 1,
        "labeldia": 52
      },
      {
        "length": 1.5,
        "width": 0.5,
        "step": 0.2
      }
    ]
}
```

Depending on the available space and detail that needs to be displayed we can of course more than 1 subdivisions, so the following is a valid description,

```
"ticks": [
  {
    "values": true,
    "decimals": 1,
    "size": 12,
    "font": 1,
    "bold": true,
    "length": 3,
    "width": 0.5,
    "step": 1,
    "labeldia": 52
  },
  {
    "length": 1.5,
    "width": 0.5,
    "step": 0.2
  },
  {
    "length": 0.75,
    "width": 0.25,
    "step": 0.1
  }
]
```

---
**HINT**

In case you need the scale to run in reverse (that is, the scale to increase counter-clock-wise) this can be achieved with the following trick:
* reverse the _min_ , _max_ values 
* use a negative _step_
* if there is a subdivision scale, that _step_ must be made negative too

the previous example can be changed like this

```
"scale": {
    "min": 10,
    "max": 0,
    "ticks": [
      {
        "values": true,
        "decimals": 1,
        "size": 12,
        "font": 1,
        "bold": true,
        "length": 3,
        "width": 0.5,
        "step": -1,         
        "labeldia": 52
      },
      {
        "length": 1.5,
        "width": 0.5,
        "step": -0.2  
      }
    ]
}
```

---

###### Custom scales
This is a bit more complex description that can accommodate cases where the scale is marked in any non-linear way. This way allows us to put the marker and the associated text at any given point around the dial specified by its angle as measured from the starting point. This is ideal for cases that we are able to measure and calibrate the scale manually so we can use those values to generate the description.

```
"scale": {
    "ticks": [
      {
        "values": true,
        "length": 3,
        "width": 0.5,
        "decimals": 1,
        "size": 12,
        "font": 1,
        "bold": true,
        "angles": [
          0,
          40,
          80,
          120,
          180,
          200
        ],
        "text": [
          ".174",
          ".175",
          ".176",
          ".177",
          ".178",
          ".179"
        ]
      }
    ]
 }
```

in this case, there is no _min_ and _max_ values specified, because the markings are prescribed in full so there is no auto-placing equally around the scaled. Most of the properties are common with the min-max scale but here the markings and labels are described in pairs in the _angles_ and _text_ items. Both together define the position and text of the marking. It is obvious that there must be 1:1 matching between the contents of _angles_ and _text_.

in this case,  the ticks support the following properties:
* _angles_ - a list (comma separated values) of angles as measured from the beginning of the scale, where tha tick mark will be placed.
* _test_ - a list (comma separated values) of text to place over every mark.Can be an empty string (i.e "") if a marker is not required to have text, but cannot be omitted.  

As with min-max scale, a non-liner scale can become arbitrary complex depending on space and needs. In the example below, a secondary scale is added to show subdivisions, extending the previous example,

```
"scale": {
    "ticks": [
      {
        "values": true,
        "length": 3,
        "width": 0.5,
        "decimals": 1,
        "size": 12,
        "font": 1,
        "bold": true,
        "angles": [
          0,
          40,
          80,
          120,
          180,
          200
        ],
        "text": [
          ".174",
          ".175",
          ".176",
          ".177",
          ".178",
          ".179"
        ]
      },
      {
        "length": 1.5,
        "width": 0.5,
        "angles": [
          10,
          20,
          30,
          50,
          60,
          70,
          90,
          100,
          110,
          140,
          155,
          165,
          190,
          195,
          210,
          220,
          230,
          240
        ]
      }
    ]
  }
},
```

#### [Internal fonts]
For simplicity the application supports a small set of fonts that can be referenced in the _font_ attribute by index.It is not possible to reference and use a font that is installed on your computer.

| Font index | Regular Font | Bold font |
| ------ | ------ | ------ |
| 1 | Courier | Courier Bold
| 2 | Courier Oblique | Courier Oblique Bold
| 3 | Times Regular | Times Bold
| 4 | Times Italic | Times Italic Bold
| 5 | Helvetica Regular | Helvetica Regular Bold
| 6 | Helvetica Oblique | Helvetica Oblique Bold

see also [full sample of fonts]

### A few complete examples
Have a look at the examples...

This is the description of a panel for a microphone compressor I made. 
The description is available in [this file](samples/mic_proc_panel.json) and the rendered output can be found [here](samples/mic_proc_panel.pdf)

This is the description of a [Regen receiver](samples/regen_receiver.json) which was abandoned. 
The controls do not make a lot of sense but demonstrate the non-linear dial.The rendered output can be found [here](samples/regen_receiver.pdf)

Another more complex example of a ginal generator
The description is available in [this file](samples/signal_generator.json) and the rendered output can be found [here](samples/signal_generator.json)

[A quick introduction to JSON]: <https://www.digitalocean.com/community/tutorials/an-introduction-to-json>

[An Introduction to JSON - Syntax and structure]: <https://www.digitalocean.com/community/tutorials/an-introduction-to-json>

[full sample of fonts]:
<font_samples.pdf>
