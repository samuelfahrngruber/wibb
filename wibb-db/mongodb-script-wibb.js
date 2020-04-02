conn = new Mongo();
db = conn.getDB("wibbdb");

db.beers.drop();

db.beers.insert({ name: "Stiegl", icon: "/res/img/beer/stiegl.png", meta: { iconBg: "#ddf6ea" } });
db.beers.insert({ name: "Gösser", icon: "/res/img/beer/goesser.png", meta: { iconBg: "#296e3b" } });
db.beers.insert({ name: "Hirter", icon: "/res/img/beer/hirter.png", meta: { iconBg: "#281b81" } });
db.beers.insert({ name: "Villacher", icon: "/res/img/beer/villacher.png", meta: { iconBg: "#FFFFFF" } });
db.beers.insert({ name: "Puntigamer", icon: "/res/img/beer/puntigamer.png", meta: { iconBg: "#0027af" } });
db.beers.insert({ name: "Murauer", icon: "/res/img/beer/murauer.png", meta: { iconBg: "#ffff00" } });
db.beers.insert({ name: "Zipfer", icon: "/res/img/beer/zipfer.png", meta: { iconBg: "#cacaca" } });
db.beers.insert({ name: "Wieselburger", icon: "/res/img/beer/wieselburger.png", meta: { iconBg: "#fef7e2" } });
db.beers.insert({ name: "Egger", icon: "/res/img/beer/egger.png", meta: { iconBg: "#2f593a" } });

db.stores.drop();

db.stores.insert({ name: "Adeg", icon: "/res/img/store/adeg.png", meta: { iconBg: "#60c037" } });
db.stores.insert({ name: "Billa", icon: "/res/img/store/billa.png", meta: { iconBg: "#ff3c19" } });
db.stores.insert({ name: "Hofer", icon: "/res/img/store/hofer.png", meta: { iconBg: "#00136b" } });
db.stores.insert({ name: "Merkur", icon: "/res/img/store/merkur.png", meta: { iconBg: "#37b678" } });
db.stores.insert({ name: "MPreis", icon: "/res/img/store/mpreis.png", meta: { iconBg: "#ff0800" } });
db.stores.insert({ name: "Spar", icon: "/res/img/store/spar.png", meta: { iconBg: "#FFFFFF" } });
db.stores.insert({ name: "Eurospar", icon: "/res/img/store/eurospar.png", meta: { iconBg: "#FFFFFF" } });
db.stores.insert({ name: "Interspar", icon: "/res/img/store/interspar.png", meta: { iconBg: "#FFFFFF" } });
db.stores.insert({ name: "T&G", icon: "/res/img/store/tundg.png", meta: { iconBg: "#2b60a9" } });

db.offers.drop();
