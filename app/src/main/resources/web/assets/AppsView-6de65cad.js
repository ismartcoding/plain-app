import{b as X,u as Y,_ as ee,a as te}from"./list-e3388e63.js";import{_ as oe}from"./FieldId-7f31c269.js";import{o as i,c as r,b as e,d as se,p as le,r as p,u as ne,s as ae,a as ue,A as ce,n as ie,B as de,Z as pe,i as re,t as _e,$ as me,G as he,e as f,w as $,g as n,f as s,v as ve,J as w,K as fe,L as $e,M as z,N as be,F as U,y as ye,x as ke,j as ge,l as qe,O as F,P as I,k as K,Q as we,z as b,R as y}from"./index-bf00d94e.js";import{_ as Te}from"./Breadcrumb-a2d00dde.js";import{n as Ve}from"./list-6498ebd9.js";import{c as Ce,b as L}from"./search-26cd7c25.js";import{u as Se,a as Ae}from"./files-0cf0fe0f.js";import"./VModal.vuevuetypescriptsetuptruelang-31725e85.js";import"./baseIndexOf-70b929c6.js";import"./toInteger-af75cc11.js";const De={viewBox:"0 0 24 24",width:"1.2em",height:"1.2em"},Be=e("path",{fill:"currentColor",d:"M12 15.575q-.2 0-.375-.062T11.3 15.3l-3.6-3.6q-.275-.275-.275-.7t.275-.7q.275-.275.713-.287t.712.262L11 12.15V5q0-.425.288-.713T12 4q.425 0 .713.288T13 5v7.15l1.875-1.875q.275-.275.713-.263t.712.288q.275.275.275.7t-.275.7l-3.6 3.6q-.15.15-.325.213t-.375.062ZM6 20q-.825 0-1.413-.588T4 18v-2q0-.425.288-.713T5 15q.425 0 .713.288T6 16v2h12v-2q0-.425.288-.713T19 15q.425 0 .713.288T20 16v2q0 .825-.588 1.413T18 20H6Z"},null,-1),xe=[Be];function Me(N,d){return i(),r("svg",De,xe)}const Qe={name:"material-symbols-download-rounded",render:Me},ze={class:"v-toolbar"},Ue={class:"right-actions"},Fe={class:"row mb-3"},Ie={class:"col-md-3 col-form-label"},Ke={class:"col-md-9"},Le=["onKeyup"],Ne={class:"actions"},Re=["onClick"],Ge={class:"table"},Ze=["onClick"],Pe=["onUpdate:modelValue"],je=e("br",null,null,-1),Ee={class:"nowrap"},He=["title"],Je=["title"],Oe={key:0},We={colspan:"7"},Xe={class:"no-data-placeholder"},it=se({__name:"AppsView",setup(N){var M,Q;const d=le(),u=p([]),{t:T}=ne(),{app:R}=ae(ue()),_=ce({text:"",tags:[]}),{downloadItems:V}=Se(u,"apps.zip"),{downloadFile:G}=Ae(R),C=ie(),S=C.query,m=p(parseInt(((M=S.page)==null?void 0:M.toString())??"1")),h=50,v=p(0),a=p(de(((Q=S.q)==null?void 0:Q.toString())??"")),A=Ce(a.value),c=C.params.type;c&&A.push({name:"type",op:"",value:c});const Z=p(L(A)),{deleteItems:P}=X(pe,()=>{E()},u);T("delete");const{selectAll:k,toggleSelect:D}=Y(u),{loading:j,refetch:E}=re({handle:(o,l)=>{l?_e(T(l),"error"):o&&(u.value=o.apps.map(g=>({...g,checked:!1})),v.value=o.appCount)},document:me,variables:()=>({offset:(m.value-1)*h,limit:h,query:Z.value}),appApi:!0});he(m,o=>{c?b(d,`/apps/${c}?page=${o}&q=${y(a.value)}`):b(d,`/apps?page=${o}&q=${y(a.value)}`)});function B(){const o=[];_.text&&o.push({name:"text",op:"",value:_.text}),a.value=L(o),x()}function x(){c?b(d,`/apps/${c}?q=${y(a.value)}`):b(d,`/apps?q=${y(a.value)}`)}return(o,l)=>{const g=Te,H=ee,J=Qe,O=oe,W=te;return i(),r(U,null,[e("div",ze,[f(g,{current:()=>`${o.$t("page_title.apps")} (${v.value})`},null,8,["current"]),e("div",Ue,[e("button",{type:"button",class:"btn btn-action",onClick:l[0]||(l[0]=$((...t)=>n(V)&&n(V)(...t),["stop"]))},s(o.$t("download")),1),f(H,{modelValue:a.value,"onUpdate:modelValue":l[2]||(l[2]=t=>a.value=t),search:x},{filters:ve(()=>[e("div",Fe,[e("label",Ie,s(o.$t("keywords")),1),e("div",Ke,[w(e("input",{type:"text","onUpdate:modelValue":l[1]||(l[1]=t=>_.text=t),class:"form-control",onKeyup:$e(B,["enter"])},null,40,Le),[[fe,_.text]])])]),e("div",Ne,[e("button",{type:"button",class:"btn",onClick:$(B,["stop"])},s(o.$t("search")),9,Re)])]),_:1},8,["modelValue"])])]),e("table",Ge,[e("thead",null,[e("tr",null,[e("th",null,[w(e("input",{class:"form-check-input",type:"checkbox",onChange:l[3]||(l[3]=(...t)=>n(D)&&n(D)(...t)),"onUpdate:modelValue":l[4]||(l[4]=t=>be(k)?k.value=t:null)},null,544),[[z,n(k)]])]),e("th",null,s(o.$t("name")),1),e("th",null,s(o.$t("version")),1),e("th",null,s(o.$t("size")),1),e("th",null,s(o.$t("type")),1),e("th",null,s(o.$t("installed_at")),1),e("th",null,s(o.$t("updated_at")),1)])]),e("tbody",null,[(i(!0),r(U,null,ye(u.value,t=>(i(),r("tr",{key:t.id,class:ke({checked:t.checked}),onClick:$(q=>t.checked=!t.checked,["stop"])},[e("td",null,[w(e("input",{class:"form-check-input",type:"checkbox","onUpdate:modelValue":q=>t.checked=q},null,8,Pe),[[z,t.checked]])]),e("td",null,[e("strong",null,[ge(s(t.name)+" ",1),f(J,{class:"bi bi-btn",onClick:$(q=>n(G)(t.path,`${t.name.replace(" ","")}-${t.id}.apk`),["stop"])},null,8,["onClick"])]),je,f(O,{id:t.id,raw:t},null,8,["id","raw"])]),e("td",null,s(t.version),1),e("td",null,s(n(qe)(t.size)),1),e("td",Ee,s(o.$t("app_type."+t.type)),1),e("td",{class:"nowrap",title:n(F)(t.installedAt)},s(n(I)(t.installedAt)),9,He),e("td",{class:"nowrap",title:n(F)(t.updatedAt)},s(n(I)(t.updatedAt)),9,Je)],10,Ze))),128))]),u.value.length?K("",!0):(i(),r("tfoot",Oe,[e("tr",null,[e("td",We,[e("div",Xe,s(o.$t(n(Ve)(n(j)))),1)])])]))]),v.value>h?(i(),we(W,{key:0,modelValue:m.value,"onUpdate:modelValue":l[5]||(l[5]=t=>m.value=t),total:v.value,limit:h},null,8,["modelValue","total"])):K("",!0)],64)}}});export{it as default};