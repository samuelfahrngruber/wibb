# Webservice Route Protocol

## POST `wibb.at/api/offers`
```
{
   "beer": {
      "name":"Stiegl",
      "icon":"/res/img/beer/stiegl.png"
   },
   "store": {
      "name":"Adeg",
      "icon":"/res/img/store/adeg.png"
   },
   "price":23,
   "startDate":"2020-02-13T00:00:00.000Z",
   "endDate":"2020-02-28T00:00:00.000Z"
}
```
*beer and store in an offer will be validated on the server

## GET `wibb.at/api/offers`
```
[
   {
      "_id":"5e357e1e9906c0185ca9b5e0",
      "beer":{
         "name":"Gösser",
         "icon":"/res/img/beer/goesser.png"
      },
      "store":{
         "name":"Spar",
         "icon":"/res/img/store/spar.png"
      },
      "price":15,
      "startDate":"2020-02-12T00:00:00.000Z",
      "endDate":"2020-02-29T00:00:00.000Z"
   },
   {
      "_id":"5e35b5249beaf41884b8d3d1",
      "beer":{
         "name":"Gösser",
         "icon":"/res/img/beer/goesser.png"
      },
      "store":{
         "name":"Billa",
         "icon":"/res/img/store/billa.png"
      },
      "price":13,
      "startDate":"2020-01-29T00:00:00.000Z",
      "endDate":"2020-02-29T00:00:00.000Z"
   },
  
   ...
]
```

## GET `wibb.at/api/beers`
```
[
   {
      "_id":"5e2ff2f98e72efe07075507d",
      "name":"Stiegl",
      "icon":"/res/img/beer/stiegl.png"
   },
   {
      "_id":"5e2ff2f98e72efe07075507e",
      "name":"Gösser",
      "icon":"/res/img/beer/goesser.png"
   },
   {
      "_id":"5e2ff2f98e72efe07075507f",
      "name":"Hirter",
      "icon":"/res/img/beer/hirter.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755080",
      "name":"Villacher",
      "icon":"/res/img/beer/villacher.png"
   },
   ...
]
```

## GET `wibb.at/api/stores`
```
[
   {
      "_id":"5e2ff2f98e72efe070755081",
      "name":"Adeg",
      "icon":"/res/img/store/adeg.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755082",
      "name":"Billa",
      "icon":"/res/img/store/billa.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755083",
      "name":"Hofer",
      "icon":"/res/img/store/hofer.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755084",
      "name":"Merkur",
      "icon":"/res/img/store/merkur.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755085",
      "name":"MPreis",
      "icon":"/res/img/store/mpreis.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755086",
      "name":"Spar",
      "icon":"/res/img/store/spar.png"
   },
   {
      "_id":"5e2ff2f98e72efe070755087",
      "name":"T&G",
      "icon":"/res/img/store/tundg.png"
   },
   ...
]
```
