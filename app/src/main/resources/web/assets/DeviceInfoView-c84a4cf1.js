import{_ as V}from"./Breadcrumb-f52b6c6b.js";import{d as $,u as I,r as p,g as A,R as D,c as l,a,p as w,t as o,F as r,J as i,x as N,a9 as T,bx as B,o as t,m as y,j as d,Y as m,h as u,Z as f,_ as j}from"./index-0c42270c.js";const x={class:"page-container"},F={class:"main"},E={class:"v-toolbar"},L={class:"panel-container"},Q={class:"grid"},S={class:"g-col-6 g-col-md-4"},C={class:"card"},G={class:"card-body"},J={class:"card-title"},P={class:"card-text"},R={class:"key-value"},Y={class:"key"},Z={class:"value"},q={key:0,class:"time"},z={class:"g-col-6 g-col-md-4"},H={class:"card"},K={class:"card-body"},M={class:"card-title"},O={class:"card-text"},U={class:"key-value"},W={class:"key"},X={class:"value"},ee={key:0,class:"time"},ae={class:"g-col-6 g-col-md-4"},se={class:"card"},le={class:"card-body"},te={class:"card-title"},oe={class:"card-text"},ce={class:"key-value"},ne={class:"key"},re={class:"value"},ie={key:0,class:"time"},de=$({__name:"DeviceInfoView",setup(ue){const{t:_}=I(),b=p([]),g=p([]),k=p([]);return A({handle:(n,h)=>{if(h)N(_(h),"error");else{const s=n.deviceInfo;b.value=[{label:"device_name",value:s.deviceName},{label:"model",value:s.model},{label:"manufacturer",value:s.manufacturer},{label:"device",value:s.device},{label:"board",value:s.board},{label:"hardware",value:s.hardware},{label:"brand",value:s.buildBrand},{label:"build_fingerprint",value:s.fingerprint}],s.phoneNumbers.length>0&&b.value.push({label:"phone_number",value:s.phoneNumbers.map(e=>e.name+" "+e.number)}),g.value=[{label:"android_version",value:s.releaseBuildVersion+" ("+s.sdkVersion+")"},{label:"security_patch",value:s.securityPatch},{label:"bootloader",value:s.bootloader},{label:"build_number",value:s.displayVersion},{label:"baseband",value:s.radioVersion},{label:"java_vm",value:s.javaVmVersion},{label:"kernel",value:s.kernelVersion},{label:"opengl_es",value:s.glEsVersion},{label:"uptime",value:T(s.uptime/1e3)}];const c=n.battery;k.value=[{label:"health",value:_(`battery_health.${c.health}`)},{label:"remaining",value:`${c.level}%`},{label:"status",value:_(`battery_status.${c.status}`)},{label:"power_source",value:_(`battery_plugged.${c.plugged}`)},{label:"technology",value:c.technology},{label:"temperature",value:`${c.temperature} ℃`},{label:"voltage",value:`${c.voltage} mV`},{label:"capacity",value:c.capacity+" mAh"}]}},document:B,appApi:!0}),(n,h)=>{const s=V,c=D("tooltip");return t(),l("div",x,[a("div",F,[a("div",E,[w(s,{current:()=>n.$t("device_info")},null,8,["current"])]),a("div",L,[a("div",Q,[a("div",S,[a("section",C,[a("div",G,[a("h5",J,o(n.$t("device")),1),a("p",P,[(t(!0),l(r,null,i(b.value,e=>(t(),l("div",R,[a("div",Y,o(n.$t(e.label)),1),a("div",Z,[e.isTime?y((t(),l("span",q,[u(o(d(f)(e.value)),1)])),[[c,d(m)(e.value)]]):Array.isArray(e.value)?(t(!0),l(r,{key:1},i(e.value,v=>(t(),l("div",null,o(v),1))),256)):(t(),l(r,{key:2},[u(o(e.value),1)],64))])]))),256))])])])]),a("div",z,[a("section",H,[a("div",K,[a("h5",M,o(n.$t("system")),1),a("p",O,[(t(!0),l(r,null,i(g.value,e=>(t(),l("div",U,[a("div",W,o(n.$t(e.label)),1),a("div",X,[e.isTime?y((t(),l("span",ee,[u(o(d(f)(e.value)),1)])),[[c,d(m)(e.value)]]):Array.isArray(e.value)?(t(!0),l(r,{key:1},i(e.value,v=>(t(),l("div",null,o(v),1))),256)):(t(),l(r,{key:2},[u(o(e.value),1)],64))])]))),256))])])])]),a("div",ae,[a("section",se,[a("div",le,[a("h5",te,o(n.$t("battery")),1),a("p",oe,[(t(!0),l(r,null,i(k.value,e=>(t(),l("div",ce,[a("div",ne,o(n.$t(e.label)),1),a("div",re,[e.isTime?y((t(),l("span",ie,[u(o(d(f)(e.value)),1)])),[[c,d(m)(e.value)]]):Array.isArray(e.value)?(t(!0),l(r,{key:1},i(e.value,v=>(t(),l("div",null,o(v),1))),256)):(t(),l(r,{key:2},[u(o(e.value),1)],64))])]))),256))])])])])])])])])}}});const be=j(de,[["__scopeId","data-v-c9cf5e1a"]]);export{be as default};