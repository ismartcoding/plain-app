import{o as r,c as l,a as c,d as S,u as V,e as w,r as D,h as L,x as B,aS as H,P as N,Q as o,R as A,S as G,j as I,t as m,m as E,w as v,p as F,F as b,J,I as P,k as U,bJ as T,a3 as p,i as C,bK as j,U as z,C as K,bL as R,X,aH as Z,bM as O,bN as W,ad as Y}from"./index-6ca6ab55.js";import{E as q}from"./EditValueModal-e18e88d9.js";const ee={viewBox:"0 0 24 24",width:"1.2em",height:"1.2em"},te=c("path",{fill:"currentColor",d:"M12 19q-.425 0-.713-.288T11 18v-5H6q-.425 0-.713-.288T5 12q0-.425.288-.713T6 11h5V6q0-.425.288-.713T12 5q.425 0 .713.288T13 6v5h5q.425 0 .713.288T19 12q0 .425-.288.713T18 13h-5v5q0 .425-.288.713T12 19Z"},null,-1),ae=[te];function ne(d,a){return r(),l("svg",ee,ae)}const se={name:"material-symbols-add-rounded",render:ne},oe={class:"nav-title"},ie=["onClick"],re=c("md-ripple",null,null,-1),le={class:"nav"},de=["onClick","onContextmenu"],me=S({__name:"TagFilter",props:{type:{type:String,required:!0},selected:{type:String,required:!0}},setup(d){const a=d,{t:n}=V(),k=w(),_=D([]),{refetch:i}=L({handle:(e,t)=>{t?B(n(t),"error"):e&&(_.value=e.tags)},document:H,variables:{type:a.type},appApi:!0});function x(){p(q,{title:n("add_tag"),placeholder:n("name"),mutation:()=>C({document:j,options:{update:()=>{i()}},appApi:!0}),getVariables:e=>({type:a.type,name:e})})}function M(e){const t=z([{name:"tag",op:"",value:T(e.name)}]);K(k,`/${R[a.type]}?q=${X(t)}`)}function $(e,t){e.preventDefault(),Z({x:e.x,y:e.y,items:[{label:n("rename"),onClick:()=>{p(q,{title:n("rename"),placeholder:n("name"),value:t.name,mutation:()=>C({document:O,appApi:!0}),getVariables:u=>({id:t.id,name:u}),done:()=>{i()}})}},{label:n("delete"),onClick:()=>{p(Y,{id:t.id,name:t.name,gql:W,appApi:!0,typeName:"Tag"})}}]})}const h=e=>{e===a.type&&i()},f=e=>{e.type===a.type&&i()},g=e=>{e.item.tags.length&&e.type===a.type&&i()};return N(()=>{o.on("refetch_tags",h),o.on("media_items_deleted",f),o.on("media_item_deleted",g)}),A(()=>{o.off("refetch_tags",h),o.off("media_items_deleted",f),o.off("media_item_deleted",g)}),(e,t)=>{const u=se,Q=G("tooltip");return r(),l(b,null,[c("h2",oe,[I(m(e.$t("tags"))+" ",1),E((r(),l("button",{class:"icon-button",onClick:v(x,["prevent"])},[re,F(u)],8,ie)),[[Q,e.$t("add_tag")]])]),c("ul",le,[(r(!0),l(b,null,J(_.value,s=>(r(),l("li",{onClick:v(y=>M(s),["prevent"]),key:s.id,onContextmenu:y=>$(y,s),class:P({active:d.selected&&U(T)(s.name)===d.selected})},m(s.name)+" ("+m(s.count)+") ",43,de))),128))])],64)}}});export{me as _,se as a};