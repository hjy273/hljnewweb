Appcombox = Ext.extend(Ext.form.ComboBox, {
			id : '',
			dataUrl : '',
			dataCode : 'code',
			dataText : 'text',
			single : true,
			hiddenName:'',
			allowBlank:true,
			width:220,
			initComponent : function() {
				Ext.QuickTips.init();
				Ext.apply(this, {
							emptyText : '«Î—°‘Ò',
							displayField : this.dataText,
							valueField : this.dataCode,
							singleMode : this.single,
							allowBlank :this.allowBlank,
							mode : 'remote',
							triggerAction : 'all',
							baseParams : '',
							extraItemCls : 'x-tag',
							width : this.width,
							editable : false,
							hiddenName:this.hiddenName,
							id:this.id,
							//onTriggerClick:Ext.emptyFn,
							//autoScroll : true,
							//resizable : true,
							forceSelection : true
						});
				this.buildDataStore();
				Appcombox.superclass.initComponent.call(this);
			},
			buildDataStore : function() {
				this.store = new Ext.data.Store({
							combox:this,
							url : this.dataUrl,
							autoload : false,
							reader : new Ext.data.JsonReader({}, [{
												name : this.dataCode
											}, {
												name : this.dataText
											}]),
							baseParams : this.baseParams
						})
				this.store.load();
			},	
			setComboValue : function(id, text) {
				if (this.hiddenField) {
					this.hiddenField.value = id;
				}
				Ext.form.ComboBox.superclass.setValue.call(this, text);
				this.value = id;
			},
			filter:function(filterFn){
				this.store.on("load", 
					function(store, records, options){
						store.filterBy(filterFn);
					}
				)
			}
			
		});
Ext.reg('Appcombox', Appcombox);
Stacombox = Ext.extend(Ext.form.ComboBox, {
			data:[],
			fields:[],
			allowBlank:true,
			dataCode : 'code',
			dataText : 'text',
			width:220,
			initComponent : function() {
				Ext.QuickTips.init();
				Ext.apply(this, {
							emptyText : '«Î—°‘Ò',
							displayField : this.dataText,
							valueField : this.dataCode,
							allowBlank :this.allowBlank,
						    editable : false,
	   						triggerAction : 'all',
	   						mode : 'local'
						});
				this.buildDataStore();
				Stacombox.superclass.initComponent.call(this);
			},
			buildDataStore : function() {
				this.store = new  Ext.data.SimpleStore({
	    			fields : this.fields,
   					data : this.data
				});
			},	
			setComboValue : function(id, text) {
				if (this.hiddenField) {
					this.hiddenField.value = id;
				}
				Ext.form.ComboBox.superclass.setValue.call(this, text);
				this.value = id;
			}
		});
Ext.reg('Stacombox', Stacombox);