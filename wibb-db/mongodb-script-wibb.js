conn = new Mongo();
db = conn.getDB("wibbdb");

db.beers.drop();

db.beers.insert({ name: "Stiegl", icon: "/res/img/beer/stiegl.png" });
db.beers.insert({ name: "Gösser", icon: "/res/img/beer/goesser.png" });
db.beers.insert({ name: "Hirter", icon: "/res/img/beer/hirter.png" });
db.beers.insert({ name: "Villacher", icon: "/res/img/beer/villacher.png" });

db.stores.drop();

db.stores.insert({ name: "Adeg", icon: "/res/img/store/adeg.png" });
db.stores.insert({ name: "Billa", icon: "/res/img/beer/billa.png" });
db.stores.insert({ name: "Hofer", icon: "/res/img/beer/hofer.png" });
db.stores.insert({ name: "Merkur", icon: "/res/img/beer/merkur.png" });
db.stores.insert({ name: "MPreis", icon: "/res/img/beer/mpreis.png" });
db.stores.insert({ name: "Spar", icon: "/res/img/beer/spar.png" });
db.stores.insert({ name: "T&G", icon: "/res/img/beer/tundg.png" });