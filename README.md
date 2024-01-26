# CSVPlatformer

CSVPlatformer is a simple 3D platformer game that can use CSV files to create levels made with JMonkey Engine 3.

The game is in very early development.

## Controls

`A S D W` to move

`SPACE` to jump

`R` to load a random level

`O` to load a file level

## CSV Levels

You can use CSV files to create levels, these files have some restrictions:

- Comma separator
- No headers
- No empty cells
- The cell contents should be int or float

Each cell of the csv will create a platform in the corresponding column and row using the cell content as height of a
platform, if the height is <= 0 the platform will not be created.

The initial platform has a height of 100.

<details>
<summary>Simple CSV Example</summary>

```csv
101,99,100,0,97,85,103
102,98,0,100,128,115,107
103,97,100,0,77,89,97
104,96,0,100,105,122,105
105,95,100,0,95,93,95
106,94,0,100,128,121,90
107,93,100,0,73,89,102
108,92,0,100,111,102,81
109,91,100,0,92,115,74
110,90,0,100,92,101,99
111,89,100,0,105,81,92
112,88,0,100,94,107,113
113,87,100,0,97,104,108
114,86,0,100,76,104,84
115,85,100,0,104,73,96
116,84,0,100,88,90,81
117,83,100,0,111,91,72
118,82,0,100,119,80,126
119,81,100,0,129,100,80
120,80,0,100,81,100,110
121,79,100,0,74,114,104
122,78,0,100,94,113,73
123,77,100,0,110,103,110
124,76,0,100,98,103,111
125,75,100,0,111,129,121
126,74,0,100,75,74,130
127,73,100,0,81,71,122
128,72,0,100,78,127,87
129,71,100,0,102,127,84
130,70,0,100,104,120,130
```

</details>

## Building

CSVPlatformer uses Gradle, run or build it using your IDE or by comand line:

```bash
./gradlew run
or
./gradlew build
```

## TODO

- [ ] Code documentation
- [ ] More level files
- [ ] Tests
- [ ] GitHub Actions
- [ ] Editable config
- [ ] Sounds and music
- [ ] Textures
- [ ] More platform types and customization

## CONTRIBUTING

[Check the structure UML](/docs/uml/structure.mmd)

## ASSETS

Check the different assets used and their licenses [here](/ASSETS_LICENSE.md)

