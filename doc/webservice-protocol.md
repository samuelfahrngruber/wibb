# Webservice Route Protocol

## POST `wibb.at/offers`
```
{
  _id: ObjectId("23fec396ca")
  beer: ObjectId("h23ef31c12"),
  store: objectId("e35fcab2c4"),
  price: 12.6,
  startDate: "2019-11-03",
  endDate: "2019-11-10"
}
```



## GET `wibb.at/offers`
```
[
  {
    _id: ObjectId("23fec396ca")
    beer: { name: "Gösser", icon: "/res/goesser.png" },
    store: { name: "Adeg", icon: "/res/adeg.png" },
    price: 12.6,
    startDate: "2019-11-03",
    endDate: "2019-11-10"
  },
  
  {
    _id: ObjectId("5ca6eca21a")
    beer: { name: "Hirter", icon: "/res/hirter.png" },
    store: { name: "Billa", icon: "/res/billa.png" },
    price: 10.2,
    startDate: "2019-11-20",
    endDate: "2019-12-02"
  }
  
  ...
]
```

## GET `wibb.at/beer`
```
[
  {
    _id: ObjectId("bc43ead451"),
    name: "Gösser",
    icon: "https://wibb.at/res/goesser.png"
  },
  
  {
    _id: ObjectId("h23ef31c12"),
    name: "Heineken",
    icon: "https://wibb.at/res/heineken.png"
  }
  
  ...
]
```

## GET `wibb.at/stores`
```
[
  {
    _id: ObjectId("e35fcab2c4"),
    name: "Hofer",
    icon: "https://wibb.at/res/hofer.png"
  },
  
  {
    _id: ObjectId("de5ca345cc"),
    name: "Spar",
    icon: "https://wibb.at/res/spar.png"
  }
  
  ...
]
```
