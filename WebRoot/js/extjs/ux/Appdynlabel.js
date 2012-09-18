Appdynlabel = Ext.extend(Ext.BoxComponent, { 
		  dataUrl : '',
		  dataCode : 'code',
		  dataText : 'text', 
		  defaultValue:'',
          tpl: new Ext.Template(
            "<span>",  
                "{0}",  
            "</span>"
            ),  
           autoEl: {  
                  tag: 'ul',  
                  cls: 'x-appdynlabel'  
           },  
           autoHide:false,  
           onRender: function() {  
                    Appdynlabel.superclass.onRender.apply(this, arguments);  
                    this.buildDataStore();
           },   
          _display: function(msg) {  
                   this.tpl.insertFirst(this.el, [msg]);  
                   this.el.scrollTo("top", 0, true);  
          },
          _filter:function(){
             this.store.each(function(record) {   
    				alert(record.get(this.dataCode));   
		     }); 
          },
          buildDataStore : function() {
				this.store = new Ext.data.Store({
				            comp:this,
				            key:this.dataCode.trim(),
							url : this.dataUrl,
							autoload : true,
							reader : new Ext.data.JsonReader({}, [{
												name : this.dataCode
											}, {
												name : this.dataText
											}]),
							baseParams : this.baseParams,
							listeners: {
                					load: function() { 
                						var date_code = this.comp.dataCode;
                						var date_text = this.comp.dataText;
                						var key = this.comp.defaultValue;
                						var temp = '';
									   	this.each(function(record) {   
									   		if(record.get(date_code)==key){
									   			temp = record.get(date_text);
									   		}  
									   	}); 
										this.comp._display(temp); 
                				}  
            				}
				})
				this.store.load();
			}
});
Ext.reg('Appdynlabel', Appdynlabel);