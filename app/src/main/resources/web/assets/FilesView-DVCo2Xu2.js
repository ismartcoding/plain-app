import{_ as no}from"./more-vert-zjmMMiPD.js";import{o as n,c as l,a as t,d as Ge,u as We,I as je,h as T,S as Je,e as S,p,x as h,w as _,y as so,A as ao,t as r,O as $,f as Xe,F as Ye,G as Ze,by as et,H as tt,bz as lo,g as io,s as de,i as co,aF as ro,aH as Ae,bA as uo,bB as _o,bC as po,K as ho,bD as mo,ai as Ue,j as vo,L as fo,M as ko,m as s,P as ue,v as k,q as u,aL as go,Q as yo,ar as $o,C as bo,bE as wo,bF as Co,bG as To,Z as G,aP as qo,ao as Io,V as _e,N as Do,aC as So,E as F,U as Vo,W as Fo,bj as Eo,X as Lo,Y as No,aq as Bo,bH as Mo,aS as zo,bI as Ho,bJ as Ke,bK as Re,ak as Oe,bL as Qe,a0 as Ao,a1 as W,ae as Uo,as as Ko,a3 as Ro,bM as Oo,bN as Qo,bO as xo}from"./index-R8dLcqG_.js";import{_ as Po}from"./sort-rounded-CsJpi3ce.js";import{_ as Go}from"./refresh-rounded-D8TYDVGT.js";import{_ as Wo}from"./content-paste-rounded-DUKWQrlf.js";import{_ as jo}from"./upload-rounded-CQIzYelP.js";import{_ as Jo}from"./search-rounded-CULEMJ2O.js";import{_ as ot}from"./default-BQXvEOng.js";import{E as xe}from"./EditValueModal-O1aAFYgx.js";import{u as Xo,c as Yo}from"./key-events-BNCM8SMB.js";import"./vee-validate.esm-vpr6zxQb.js";const Zo={viewBox:"0 0 24 24",width:"1.2em",height:"1.2em"},en=t("path",{fill:"currentColor",d:"M4 20q-.825 0-1.412-.587T2 18V6q0-.825.588-1.412T4 4h5.175q.4 0 .763.15t.637.425L12 6h8q.825 0 1.413.588T22 8v10q0 .825-.587 1.413T20 20zm0-2h16V8h-8.825l-2-2H4zm0 0V6zm10-4v1q0 .425.288.713T15 16t.713-.288T16 15v-1h1q.425 0 .713-.288T18 13t-.288-.712T17 12h-1v-1q0-.425-.288-.712T15 10t-.712.288T14 11v1h-1q-.425 0-.712.288T12 13t.288.713T13 14z"},null,-1),tn=[en];function on(y,v){return n(),l("svg",Zo,[...tn])}const nn={name:"material-symbols-create-new-folder-outline-rounded",render:on},sn={viewBox:"0 0 24 24",width:"1.2em",height:"1.2em"},an=t("path",{fill:"currentColor",d:"M19.075 21.9L17.5 20.35q-1.225.8-2.613 1.225T12 22q-2.075 0-3.9-.788t-3.175-2.137T2.788 15.9T2 12q0-1.5.425-2.887T3.65 6.5L2.075 4.925q-.3-.3-.3-.712t.3-.713t.713-.3t.712.3l17 17q.3.3.3.7t-.3.7t-.712.3t-.713-.3M12 20q1.075 0 2.088-.275t1.962-.825L5.1 7.95q-.55.95-.825 1.962T4 12q0 3.325 2.338 5.663T12 20m0-16q-.825 0-1.612.163T8.85 4.65q-.4.175-.8.05t-.6-.475t-.088-.75t.488-.575q.975-.45 2.012-.675T12 2q2.075 0 3.9.788t3.175 2.137T21.213 8.1T22 12q0 1.1-.225 2.138T21.1 16.15q-.175.375-.575.488t-.75-.088t-.475-.6t.05-.8q.325-.75.488-1.537T20 12q0-3.325-2.337-5.663T12 4m-1.425 9.425"},null,-1),ln=[an];function cn(y,v){return n(),l("svg",sn,[...ln])}const rn={name:"material-symbols-hide-source-outline-rounded",render:cn},dn=y=>(Ye("data-v-51d6a30b"),y=y(),Ze(),y),un={key:"filter-text"},_n=["label"],pn={key:"filter-show-hidden"},hn=["label"],mn=dn(()=>t("md-ripple",null,null,-1)),vn=["open"],fn={class:"filters"},kn={class:"form-row"},gn=["label"],yn=["label","selected"],$n={class:"buttons"},bn=Ge({__name:"FileSearchInput",props:{parent:{type:String,required:!0},filter:{type:Object,required:!0},getUrl:{type:Function,required:!0}},setup(y,{expose:v}){const{copyFilter:A,buildQ:j}=et(),J=We(),c=je({showHidden:!1,linkName:"",text:"",parent:""}),b=y,E=T(!1);function L(){A(c,b.filter),g(),B()}function g(){Xe(J,b.getUrl(j(b.filter)))}function w(){b.filter.text="",g()}function U(){E.value=!0,A(b.filter,c)}function B(){E.value=!1}function I(){b.filter.showHidden=!1,g()}function K(){c.showHidden=!c.showHidden}return v({dismiss:B}),(D,V)=>{const R=rn,O=Jo,X=Je("tooltip");return n(),l($,null,[t("md-chip-set",null,[b.filter.text?(n(),l("div",un,[t("md-input-chip",{label:b.filter.text,"remove-only":"",onRemove:w},null,40,_n)])):S("",!0),b.filter.showHidden?(n(),l("div",pn,[t("md-input-chip",{label:D.$t("show_hidden"),"remove-only":"",onRemove:I},[p(R,{slot:"icon"})],40,hn)])):S("",!0)]),h((n(),l("button",{id:"btn-search",class:"btn-icon",onClick:_(U,["prevent"])},[mn,p(O)])),[[X,D.$t("search")]]),t("md-menu",{positioning:"popover",anchor:"btn-search","menu-corner":"start-end","anchor-corner":"end-end","stay-open-on-focusout":"",quick:"",open:E.value,onClosed:B},[t("div",fn,[t("div",kn,[h(t("md-outlined-text-field",{label:D.$t("keywords"),"onUpdate:modelValue":V[0]||(V[0]=Y=>c.text=Y),onKeyup:ao(L,["enter"])},null,40,gn),[[so,c.text]])]),t("md-chip-set",null,[t("md-filter-chip",{key:"chip-show-hidden",label:D.$t("show_hidden"),selected:c.showHidden,onClick:K},null,8,yn)]),t("div",$n,[t("md-filled-button",{onClick:_(L,["stop"])},r(D.$t("search")),1)])])],40,vn)],64)}}}),wn=tt(bn,[["__scopeId","data-v-51d6a30b"]]),Cn={viewBox:"0 0 24 24",width:"1.2em",height:"1.2em"},Tn=t("path",{fill:"currentColor",d:"m12 14l-2.35 2.35q.2.375.275.8T10 18q0 1.65-1.175 2.825T6 22t-2.825-1.175T2 18t1.175-2.825T6 14q.425 0 .85.075t.8.275L10 12L7.65 9.65q-.375.2-.8.275T6 10q-1.65 0-2.825-1.175T2 6t1.175-2.825T6 2t2.825 1.175T10 6q0 .425-.075.85t-.275.8L20.6 18.6q.675.675.3 1.538T19.575 21q-.275 0-.537-.112t-.463-.313zm3-3l-2-2l5.575-5.575q.2-.2.463-.312T19.574 3q.95 0 1.313.875t-.313 1.55zM6 8q.825 0 1.413-.587T8 6t-.587-1.412T6 4t-1.412.588T4 6t.588 1.413T6 8m6 4.5q.2 0 .35-.15t.15-.35t-.15-.35t-.35-.15t-.35.15t-.15.35t.15.35t.35.15M6 20q.825 0 1.413-.587T8 18t-.587-1.412T6 16t-1.412.588T4 18t.588 1.413T6 20"},null,-1),qn=[Tn];function In(y,v){return n(),l("svg",Cn,[...qn])}const Dn={name:"material-symbols-content-cut-rounded",render:In},Sn={viewBox:"0 0 24 24",width:"1.2em",height:"1.2em"},Vn=t("path",{fill:"currentColor",d:"M9 18q-.825 0-1.412-.587T7 16V4q0-.825.588-1.412T9 2h9q.825 0 1.413.588T20 4v12q0 .825-.587 1.413T18 18zm0-2h9V4H9zm-4 6q-.825 0-1.412-.587T3 20V7q0-.425.288-.712T4 6t.713.288T5 7v13h10q.425 0 .713.288T16 21t-.288.713T15 22zm4-6V4z"},null,-1),Fn=[Vn];function En(y,v){return n(),l("svg",Sn,[...Fn])}const Ln={name:"material-symbols-content-copy-outline-rounded",render:En},Nn=lo({id:"files",state:()=>({selectedFiles:[],isCut:!1})}),Bn=y=>(Ye("data-v-6725f732"),y=y(),Ze(),y),Mn={class:"top-app-bar"},zn=["checked","indeterminate"],Hn={key:0},An={key:1,class:"breadcrumb"},Un={key:0},Kn=["onClick"],Rn={key:0},On=["onClick"],Qn={class:"actions"},xn=["onClick"],Pn={slot:"headline"},Gn=["onClick"],Wn={slot:"headline"},jn={class:"menu-items"},Jn=["onClick","selected"],Xn={slot:"headline"},Yn={key:0,class:"scroller-wrapper"},Zn={class:"scroller"},es={class:"start"},ts=Bn(()=>t("div",{class:"checkbox"},[t("div",{class:"skeleton-checkbox"})],-1)),os={class:"number"},ns=No('<div class="image" data-v-6725f732><div class="skeleton-image" data-v-6725f732></div></div><div class="title" data-v-6725f732><div class="skeleton-text skeleton-title" data-v-6725f732></div></div><div class="subtitle" data-v-6725f732><div class="skeleton-text skeleton-subtitle" data-v-6725f732></div></div><div class="actions" data-v-6725f732><div class="skeleton-text skeleton-actions" data-v-6725f732></div></div>',4),ss=["onClick","onMouseover"],as={class:"start"},ls=["onClick","checked"],is=["onClick","checked"],cs={class:"number"},rs=["onClick"],ds={key:0,src:"/ficons/folder.svg",class:"svg"},us={key:0,class:"svg",src:ot},_s=["src","onError"],ps=["src","onError"],hs={key:3,class:"svg",src:ot},ms={class:"title"},vs={class:"subtitle"},fs={key:0},ks={key:1},gs={class:"actions"},ys=["onClick"],$s={slot:"headline"},bs=["onClick"],ws={slot:"headline"},Cs={class:"card card-info"},Ts={class:"key-value vertical"},qs={class:"key"},Is={class:"value"},Ds=["onClick"],Ss={slot:"headline"},Vs=["onClick"],Fs={slot:"headline"},Es=["onClick"],Ls={slot:"headline"},Ns=["onClick"],Bs={slot:"headline"},Ms=["onClick"],zs={slot:"headline"},Hs={key:1,class:"no-data-placeholder"},Pe=1e4,As=Ge({__name:"FilesView",setup(y){var ze;const{t:v}=io(),A=T([]),{parseQ:j,buildQ:J}=et(),c=je({linkName:"",showHidden:!1,text:"",parent:""}),E=yo().query,L=T(""),g=T([]),{selectedIds:w,allChecked:U,realAllChecked:B,clearSelection:I,toggleAllChecked:K,toggleSelect:D,total:V,checked:R,shiftEffectingIds:O,handleItemClick:X,handleMouseOver:Y,selectAll:nt,shouldSelect:st}=Xo(g),{keyDown:pe,keyUp:he}=Yo(nt,I,()=>{Ve()}),Z=T(!1),Q=T(!1),me=T([]),ve=T([]),at=e=>{me.value.push(e)},lt=e=>{ve.value.push(e)},it=Bo(),fe=We(),{fileSortBy:x}=de(fe),ke=co(),{app:ge,urlTokenKey:M,uploads:ee}=de(ke),{selectedFiles:ct,isCut:rt}=de(Nn()),{dropping:dt,fileDragEnter:ye,fileDragLeave:$e,dropFiles:ut}=ro(ee),P=Ae(()=>Mo(c.linkName,ge.value)),{createPath:_t,createVariables:pt,createMutation:ht}=uo(M,g),{renameItem:mt,renameDone:vt,renameMutation:ft,renameVariables:kt}=_o(()=>{z()}),{internal:be,sdcard:we,usb:gt,refetch:te}=po(),{downloadFile:oe,downloadDir:yt,downloadFiles:$t}=$o(M),{view:Ce}=Qo(A,(e,o)=>{ke.lightbox={sources:e,index:o,visible:!0}}),bt=T(parseInt(((ze=E.page)==null?void 0:ze.toString())??"1")),wt=Ae(()=>{const e=[];let o=c.parent;for(;o&&o!==P.value;)e.unshift({path:o,name:zo(o)}),o=o.substring(0,o.lastIndexOf("/"));return e.unshift({path:P.value,name:Lt()}),e}),Te=T(!0),{loading:ne,fetch:z}=ho({handle:async(e,o)=>{if(Te.value=!1,Z.value=!1,Q.value=!1,o)bo(v(o),"error");else{const C=[];for(const m of e.files)C.push(wo(m,M.value));g.value=C,V.value=C.length}},document:Co,variables:()=>({root:P.value,offset:(bt.value-1)*Pe,limit:Pe,query:L.value,sortBy:x.value}),options:{fetchPolicy:"cache-and-network"},appApi:!0}),{loading:Ct,canPaste:qe,copy:se,cut:Ie,paste:ae}=mo(g,rt,ct,z,te),{input:Tt,upload:qt,uploadChanged:De}=Ue(ee),{input:It,upload:Dt,uploadChanged:Se}=Ue(ee),{loading:St,mutate:Vt,onDone:Ft}=vo({document:To,appApi:!0});Ft(e=>{$t(e.data.setTempValue.key),I()});const Et=()=>{Vt({key:Ho(),value:JSON.stringify(w.value.map(e=>({path:e})))})},le=e=>{e.forEach(o=>{xo(g.value,C=>C.id===o.id)}),I(),te()},Ve=()=>{G(Ke,{files:g.value.filter(e=>w.value.includes(e.id)),onDone:e=>{le(e)}})};function Lt(){if(c.linkName==="sdcard")return v("sdcard");if(c.linkName==="app")return v("app_name");if(c.linkName.startsWith("usb")){const e=parseInt(c.linkName.substring(3));return`${v("usb_storage")} ${e}`}return v("internal_storage")}function Fe(){var e,o,C,m;if(c.linkName==="sdcard")return`${v("storage_free_total",{free:F(((e=we.value)==null?void 0:e.freeBytes)??0),total:F(((o=we.value)==null?void 0:o.totalBytes)??0)})}`;if(c.linkName==="app")return v("app_name");if(c.linkName.startsWith("usb")){const ce=parseInt(c.linkName.substring(3)),q=gt.value[ce-1];return`${v("storage_free_total",{free:F((q==null?void 0:q.freeBytes)??0),total:F((q==null?void 0:q.totalBytes)??0)})}`}return`${F(((C=be.value)==null?void 0:C.freeBytes)??0)} / ${F(((m=be.value)==null?void 0:m.totalBytes)??0,!0,0)}`}function ie(e){I(),c.parent=e;const o=J(c);Xe(fe,Ee(o))}function Ee(e){return`/files?q=${e}`}function Nt(e){if(e.isDir){ie(e.path);return}Re(e.name)?window.open(Oe(M.value,e.path),"_blank"):Qe(e.name)?Ce(g.value,e):oe(e.path)}function Bt(e,o){o.isDir||(e.stopPropagation(),Re(o.name)?window.open(Oe(M.value,o.path),"_blank"):Qe(o.name)?Ce(g.value,o):oe(o.path))}function Mt(e,o){if(x.value===o){Q.value=!1,e.close();return}Q.value=!0,x.value=o,e.close()}function zt(){Z.value=!0,z()}const Ht=()=>{_t.value=c.parent,G(xe,{title:v("name"),placeholder:v("name"),mutation:ht,getVariables:pt})};function Le(e,o){qt(o),e.close()}function Ne(e,o){Dt(o),e.close()}function At(){se(w.value),I()}function Ut(){Ie(w.value),I()}function Kt(){ae(c.parent)}function Rt(e,o){se([o.id]),ae(c.parent),e.close()}function Ot(e,o){Ie([o.id]),e.close()}function Qt(e,o){se([o.id]),e.close()}function xt(e,o){ae(o.path),e.close()}function Pt(e,o){mt.value=o,G(xe,{title:v("rename"),placeholder:v("name"),value:o.name,mutation:ft,getVariables:kt,done:vt}),e.close()}function Gt(e){G(Ke,{files:[e],onDone:le})}const Be=e=>{e.status==="done"&&setTimeout(()=>{z(),te()},1e3)},Me=e=>{le([e.item])};function Wt(e){ut(e,c.parent)}return fo(()=>{var e;L.value=Ao(((e=E.q)==null?void 0:e.toString())??""),j(c,L.value),z(),W.on("upload_task_done",Be),W.on("file_deleted",Me),window.addEventListener("keydown",pe),window.addEventListener("keyup",he)}),ko(()=>{W.off("upload_task_done",Be),W.off("file_deleted",Me),window.removeEventListener("keydown",pe),window.removeEventListener("keyup",he)}),(e,o)=>{const C=Ln,m=qo,ce=Dn,q=Uo,re=Ko,jt=wn,Jt=nn,He=jo,H=Io,Xt=Wo,Yt=Go,Zt=Po,eo=Ro,to=Oo,oo=no,f=Je("tooltip");return n(),l($,null,[t("div",Mn,[t("md-checkbox",{"touch-target":"wrapper",onChange:o[0]||(o[0]=(...i)=>s(K)&&s(K)(...i)),checked:s(U),indeterminate:!s(U)&&s(R)},null,40,zn),s(w).length?(n(),l("span",Hn,r(e.$t("x_selected",{count:s(B)?s(V).toLocaleString():s(w).length.toLocaleString()})),1)):(n(),l("div",An,[(n(!0),l($,null,ue(wt.value,(i,a)=>(n(),l($,{key:i.path},[a===0?(n(),l($,{key:0},[i.path===c.parent?h((n(),l("span",Un,[_e(r(i.name)+" ("+r(s(V))+")",1)])),[[f,Fe()]]):h((n(),l("a",{key:1,href:"#",onClick:_(d=>ie(i.path),["stop","prevent"])},[_e(r(i.name),1)],8,Kn)),[[f,Fe()]])],64)):(n(),l($,{key:1},[i.path===c.parent?(n(),l("span",Rn,r(i.name)+" ("+r(s(V))+")",1)):(n(),l("a",{key:1,href:"#",onClick:_(d=>ie(i.path),["stop","prevent"])},r(i.name),9,On))],64))],64))),128))])),s(R)?(n(),l($,{key:2},[h((n(),k(m,{onClick:_(At,["stop"])},{icon:u(()=>[p(C)]),_:1})),[[f,e.$t("copy")]]),h((n(),k(m,{onClick:_(Ut,["stop"])},{icon:u(()=>[p(ce)]),_:1})),[[f,e.$t("cut")]]),h((n(),k(m,{onClick:_(Ve,["stop"])},{icon:u(()=>[p(q)]),_:1})),[[f,e.$t("delete")]]),h((n(),k(m,{loading:s(St),onClick:_(Et,["stop"])},{icon:u(()=>[p(re)]),_:1},8,["loading"])),[[f,e.$t("download")]])],64)):S("",!0),t("div",Qn,[p(jt,{filter:c,parent:P.value,"get-url":Ee},null,8,["filter","parent"]),h((n(),k(m,{onClick:Ht},{icon:u(()=>[p(Jt)]),_:1})),[[f,e.$t("create_folder")]]),p(H,null,{content:u(i=>[t("md-menu-item",{onClick:_(a=>Le(i,c.parent),["stop"])},[t("div",Pn,r(e.$t("upload_files")),1)],8,xn),t("md-menu-item",{onClick:_(a=>Ne(i,c.parent),["stop"])},[t("div",Wn,r(e.$t("upload_folder")),1)],8,Gn)]),default:u(()=>[h((n(),k(m,null,{icon:u(()=>[p(He)]),_:1})),[[f,e.$t("upload")]])]),_:1}),s(qe)()?h((n(),k(m,{key:0,loading:s(Ct),onClick:Kt},{icon:u(()=>[p(Xt)]),_:1},8,["loading"])),[[f,e.$t("paste")]]):S("",!0),h((n(),k(m,{loading:Z.value,onClick:zt},{icon:u(()=>[p(Yt)]),_:1},8,["loading"])),[[f,e.$t("refresh")]]),p(H,null,{content:u(i=>[t("div",jn,[(n(!0),l($,null,ue(s(it),a=>(n(),l("md-menu-item",{onClick:d=>Mt(i,a.value),key:a.value,selected:a.value===s(x)},[t("div",Xn,r(e.$t(a.label)),1)],8,Jn))),128))])]),default:u(()=>[h((n(),k(m,{loading:Q.value},{icon:u(()=>[p(Zt)]),_:1},8,["loading"])),[[f,e.$t("sort")]])]),_:1})])]),s(ne)&&Te.value?(n(),l("div",Yn,[t("div",Zn,[(n(),l($,null,ue(20,i=>t("section",{class:"file-item selectable-card-skeleton",key:i},[t("div",es,[ts,t("span",os,r(i),1)]),ns])),64))])])):S("",!0),t("div",{class:"scroller-wrapper",onDragover:o[4]||(o[4]=_((...i)=>s(ye)&&s(ye)(...i),["stop","prevent"]))},[h(t("div",{class:"drag-mask",onDrop:_(Wt,["stop","prevent"]),onDragleave:o[1]||(o[1]=_((...i)=>s($e)&&s($e)(...i),["stop","prevent"]))},r(e.$t("release_to_send_files")),545),[[go,s(dt)]]),g.value.length>0?(n(),k(s(Eo),{key:0,class:"scroller","data-key":"id","data-sources":g.value,"estimate-size":80},{item:u(({index:i,item:a})=>[t("section",{class:Do(["file-item selectable-card",{selected:s(w).includes(a.id),selecting:s(O).includes(a.id)}]),onClick:_(d=>s(X)(d,a,i,()=>{Nt(a)}),["stop"]),onMouseover:d=>s(Y)(d,i)},[t("div",as,[s(O).includes(a.id)?(n(),l("md-checkbox",{key:0,class:"checkbox","touch-target":"wrapper",onClick:_(d=>s(D)(d,a,i),["stop"]),checked:s(st)},null,8,ls)):(n(),l("md-checkbox",{key:1,class:"checkbox","touch-target":"wrapper",onClick:_(d=>s(D)(d,a,i),["stop"]),checked:s(w).includes(a.id)},null,8,is)),t("span",cs,[p(eo,{id:i+1,raw:a},null,8,["id","raw"])])]),t("div",{class:"image",onClick:d=>Bt(d,a)},[a.isDir?(n(),l("img",ds)):(n(),l($,{key:1},[ve.value.includes(a.id)?(n(),l("img",us)):!me.value.includes(a.id)&&a.fileId?(n(),l("img",{key:1,class:"image-thumb",src:s(So)(a.fileId,"&w=50&h=50"),onError:d=>at(a.id)},null,40,_s)):a.extension?(n(),l("img",{key:2,src:`/ficons/${a.extension}.svg`,class:"svg",onError:d=>lt(a.id)},null,40,ps)):(n(),l("img",hs))],64))],8,rs),t("div",ms,r(a.name),1),t("div",vs,[a.isDir?(n(),l("span",fs,r(e.$t("x_items",a.children)),1)):(n(),l("span",ks,r(s(F)(a.size)),1)),h((n(),l("span",null,[_e(r(s(Fo)(a.updatedAt)),1)])),[[f,s(Vo)(a.updatedAt)]])]),t("div",gs,[a.isDir?(n(),l($,{key:0},[h((n(),k(m,{class:"sm",onClick:_(d=>s(yt)(a.path),["stop"])},{icon:u(()=>[p(re)]),_:2},1032,["onClick"])),[[f,e.$t("download")]]),p(H,null,{content:u(d=>[t("md-menu-item",{onClick:_(N=>Le(d,a.path),["stop"])},[t("div",$s,r(e.$t("upload_files")),1)],8,ys),t("md-menu-item",{onClick:_(N=>Ne(d,a.path),["stop"])},[t("div",ws,r(e.$t("upload_folder")),1)],8,bs)]),default:u(()=>[h((n(),k(m,{class:"sm"},{icon:u(()=>[p(He)]),_:1})),[[f,e.$t("upload")]])]),_:2},1024)],64)):h((n(),k(m,{key:1,class:"sm",onClick:_(d=>s(oe)(a.path),["stop"])},{icon:u(()=>[p(re)]),_:2},1032,["onClick"])),[[f,e.$t("download")]]),h((n(),k(m,{class:"sm",onClick:_(d=>Gt(a),["stop"])},{icon:u(()=>[p(q)]),_:2},1032,["onClick"])),[[f,e.$t("delete")]]),p(H,null,{content:u(()=>[t("section",Cs,[t("div",Ts,[t("div",qs,r(e.$t("path")),1),t("div",Is,r(a.path),1)])])]),default:u(()=>[h((n(),k(m,{class:"sm"},{icon:u(()=>[p(to)]),_:1})),[[f,e.$t("info")]])]),_:2},1024),p(H,null,{content:u(d=>[t("md-menu-item",{onClick:_(N=>Rt(d,a),["stop"])},[t("div",Ss,r(e.$t("duplicate")),1)],8,Ds),t("md-menu-item",{onClick:_(N=>Ot(d,a),["stop"])},[t("div",Fs,r(e.$t("cut")),1)],8,Vs),t("md-menu-item",{onClick:_(N=>Qt(d,a),["stop"])},[t("div",Ls,r(e.$t("copy")),1)],8,Es),a.isDir&&s(qe)()?(n(),l("md-menu-item",{key:0,onClick:_(N=>xt(d,a),["stop"])},[t("div",Bs,r(e.$t("paste")),1)],8,Ns)):S("",!0),t("md-menu-item",{onClick:_(N=>Pt(d,a),["stop"])},[t("div",zs,r(e.$t("rename")),1)],8,Ms)]),default:u(()=>[h((n(),k(m,{class:"sm"},{icon:u(()=>[p(oo)]),_:1})),[[f,e.$t("actions")]])]),_:2},1024)])],42,ss)]),_:1},8,["data-sources"])):S("",!0),!s(ne)&&g.value.length===0?(n(),l("div",Hs,r(e.$t(s(Lo)(s(ne),s(ge).permissions,"WRITE_EXTERNAL_STORAGE"))),1)):S("",!0),t("input",{ref_key:"fileInput",ref:Tt,style:{display:"none"},type:"file",multiple:"",onChange:o[2]||(o[2]=(...i)=>s(De)&&s(De)(...i))},null,544),t("input",{ref_key:"dirFileInput",ref:It,style:{display:"none"},type:"file",multiple:"",webkitdirectory:"",mozdirectory:"",directory:"",onChange:o[3]||(o[3]=(...i)=>s(Se)&&s(Se)(...i))},null,544)],32)],64)}}}),Xs=tt(As,[["__scopeId","data-v-6725f732"]]);export{Xs as default};