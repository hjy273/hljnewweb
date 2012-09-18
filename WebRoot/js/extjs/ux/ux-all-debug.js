TreeComboField = Ext.extend(Ext.form.TriggerField, {
	/**
	 * @cfg {String} valueField ȡֵ�󶨵��ֶ�����Ĭ��Ϊ'id'
	 */
	valueField : "id",
	/**
	 * @cfg {String} displayField ����������ʾ���ư󶨵��ֶ�����Ĭ��Ϊ'name'
	 */
	displayField : "name",
	/**
	 * @cfg {Integer} minListWidth ��С���б���ʾ���
	 */
	minListWidth : 70,
	haveShow : false,
	/**
	 * @cfg {Boolean} editable �Ƿ�Ĭ�Ͽɱ༭ Ĭ��Ϊfalse
	 */
	editable : false,
	/**
	 * @cfg {Boolean} returnObject ����ֵ�Ƿ���Ϊ���󷵻�
	 */
	returnObject : false,
	/**
	 * @cfg {Boolean} leafOnly �Ƿ�ֻ��ѡ��Ҷ�ӽڵ㣬Ĭ�Ͽ���ѡ���κνڵ㡣
	 */
	leafOnly : false,
	/**
	 * @cfg {Integer} clicksFinishEdit �ڽڵ��ϵ����Ϊѡ��Ĵ���
	 */
	clicksFinishEdit : 1,
	/**
	 * @cfg {Boolean} readOnly �Ƿ�ֻ��
	 */
	readOnly : false,
	hiddenNodes : [],
	// private
	initEvents : function() {
		TreeComboField.superclass.initEvents.call(this);
		this.keyNav = new Ext.KeyNav(this.el, {
					"up" : function(e) {
						this.inKeyMode = true;
						this.selectPrevious();
					},

					"down" : function(e) {
						if (!this.isExpanded()) {
							this.onTriggerClick();
						} else {
							this.inKeyMode = true;
							this.selectNext();
						}
					},
					"enter" : function(e) {
						var sm = this.tree.getSelectionModel();
						if (sm.getSelectedNode()) {
							var node = sm.getSelectedNode();
							this.choice(node);
							sm.clearSelections();
							return;
						}
					},
					"esc" : function(e) {
						this.collapse();
					},
					scope : this
				});
		this.queryDelay = Math.max(this.queryDelay || 10, this.mode == 'local'
						? 10
						: 250);
		this.dqTask = new Ext.util.DelayedTask(this.initQuery, this);
		if (this.typeAhead) {
			this.taTask = new Ext.util.DelayedTask(this.onTypeAhead, this);
		}
		if (this.editable !== false) {
			this.el.on("keyup", this.onKeyUp, this);
		}
		if (this.forceSelection) {
			this.on('blur', this.doForce, this);
		}
	},
	// private
	selectPrevious : function() {
		var sm = this.tree.getSelectionModel();
		if (!sm.selectPrevious()) {
			var root = this.tree.getRootNode();
			sm.select(root);
			this.el.focus();
		} else {
			this.el.focus();
		}
	},
	setComboValue : function(id, text) {
		if (this.hiddenField) {
			this.hiddenField.value = id;
		}
		Ext.form.ComboBox.superclass.setValue.call(this, text);
		this.value = id;
	},
	// private
	selectNext : function() {
		var sm = this.tree.getSelectionModel();
		if (!sm.selectNext()) {
			var root = this.tree.getRootNode();
			sm.select(root);
			this.el.focus();
		} else {
			this.el.focus();
		}
	},
	// private
	onTriggerClick : function() {
		if (this.readOnly || this.disabled) {
			return false;
		} else if (!this.tree.rendered || !this.list) {
			this.treeId = Ext.id();
			this.list = new Ext.Layer({
						id : this.treeId,
						cls : "x-combo-list",
						constrain : false
					});
			if (!this.innerDom)
				this.innerDom = Ext.getBody().dom;
			if (this.tree.rendered) {
				this.list.appendChild(this.tree.el);
			} else {
				this.tree.render(this.treeId);
				var lw = this.listWidth
						|| Math.max(this.wrap.getWidth(), this.minListWidth);
				this.tree.setWidth(lw);
				this.tree.on("expandnode", this.restrictHeight, this);
				this.tree.on("collapsenode", this.restrictHeight, this);
			}
		} else
			this.restrictHeight();
		this.expand();
	},
	// private
	restrictHeight : function() {
		// this.list.dom.style.height = '';
		if (!this.list)
			return;
		var inner = this.innerDom;
		var h = inner.clientHeight - this.wrap.getBottom();
		if (this.tree.el.dom.offsetHeight >= h) {
			this.tree.setHeight(h);
		} else {
			this.tree.setHeight("auto");
		}
		// this.list.alignTo(this.getEl(), "tl-bl?");
	},
	// private
	filterTree : function(e) {
		if (!this.isExpanded())
			this.expand();
		var text = e.target.value;
		Ext.each(this.hiddenNodes, function(n) {
					n.ui.show();
				});
		if (!text) {
			this.filter.clear();
			return;
		}
		this.tree.expandAll();
		this.restrictHeight();
		this.filter.filterBy(function(n) {
					return (!n.attributes.leaf || n.text.indexOf(text) >= 0);
				});

		// hide empty packages that weren't filtered
		this.hiddenNodes = [];
		this.tree.root.cascade(function(n) {
					if (!n.attributes.leaf && n.ui.ctNode.offsetHeight < 3) {
						n.ui.hide();
						this.hiddenNodes.push(n);
					}
				}, this);
	},
	// private
	expand : function() {
		if (this.list) {
			Ext.getDoc().on('mousedown', this.hideIf, this);
			/*
			 * if(!this.tree.body.isScrollable()){ this.tree.setHeight('auto'); }
			 */
			this.list.show();
			this.list.alignTo(this.getEl(), "tl-bl?");
		} else {
			this.onTriggerClick();
		}
	},
	// private
	collapse : function() {
		if (this.list) {
			this.list.hide();
			Ext.getDoc().un('mousedown', this.hideIf, this);
		}
	},
	// private
	onEnable : function() {
		TreeComboField.superclass.onEnable.apply(this, arguments);
		if (this.hiddenField) {
			this.hiddenField.disabled = false;
		}
	},
	// private
	onDisable : function() {
		TreeComboField.superclass.onDisable.apply(this, arguments);
		if (this.hiddenField) {
			this.hiddenField.disabled = true;
		}
		Ext.getDoc().un('mousedown', this.hideIf, this);
	},
	// private
	hideIf : function(e) {
		if (!e.within(this.wrap) && !e.within(this.list)) {
			this.collapse();
		}
	},
	// private
	initComponent : function() {
		TreeComboField.superclass.initComponent.call(this);
		this.addEvents('beforeSetValue');
		this.filter = new Ext.tree.TreeFilter(this.tree, {
					clearBlank : true,
					autoClear : true
				});
	},
	// private
	onRender : function(ct, position) {
		TreeComboField.superclass.onRender.call(this, ct, position);
		if (this.clicksFinishEdit > 1)
			this.tree.on("dblclick", this.choice, this);
		else
			this.tree.on("click", this.choice, this);
		if (this.hiddenName) {
			this.hiddenField = this.el.insertSibling({
						tag : 'input',
						type : 'hidden',
						name : this.hiddenName,
						id : (this.hiddenId || this.hiddenName)
					}, 'before', true);
			this.hiddenField.value = this.hiddenValue !== undefined
					? this.hiddenValue
					: this.value !== undefined ? this.value : '';
			this.el.dom.removeAttribute('name');
		}
		if (!this.editable) {
			this.editable = true;
			this.setEditable(false);
		} else {
			this.el.on('keydown', this.filterTree, this, {
						buffer : 350
					});
		}
	},
	/**
	 * ����ѡ�е����ڵ�
	 * 
	 * @return {Object} ret ѡ�еĽڵ�ֵ��
	 */
	getValue : function(returnObject) {
		if ((returnObject === true) || this.returnObject)
			return typeof this.value != 'undefined' ? {
				value : this.value,
				text : this.text,
				toString : function() {
					return this.text;
				}
			} : "";
		return typeof this.value != 'undefined' ? this.value : '';
	},
	/**
	 * ���ѡ���ֵ��
	 */
	clearValue : function() {
		if (this.hiddenField) {
			this.hiddenField.value = '';
		}
		this.setRawValue('');
		this.lastSelectionText = '';
		this.applyEmptyText();
		this.value = "";
	},
	/**
	 * ��֤ѡ���ֵ
	 * 
	 * @return {Boolean} ret ���ѡ���ֵ�Ϸ�������true��
	 */
	validate : function() {
		if (this.disabled
				|| this.validateValue(this.processValue(this.getValue()))) {
			this.clearInvalid();
			return true;
		}
		return false;
	},
	/**
	 * Returns whether or not the field value is currently valid by validating
	 * the processed value of the field. Note: disabled fields are ignored.
	 * 
	 * @param {Boolean}
	 *            preventMark True to disable marking the field invalid
	 * @return {Boolean} True if the value is valid, else false
	 */
	isValid : function(preventMark) {
		if (this.disabled) {
			return true;
		}
		var restore = this.preventMark;
		this.preventMark = preventMark === true;
		var v = this.validateValue(this.processValue(this.getValue()));
		this.preventMark = restore;
		return v;
	},
	/**
	 * Validates a value according to the field's validation rules and marks the
	 * field as invalid if the validation fails
	 * 
	 * @param {Mixed}
	 *            value The value to validate
	 * @return {Boolean} True if the value is valid, else false
	 */
	validateValue : function(value) {
		if (value.length < 1 || value === null) { // if it's
			// blank
			if (this.allowBlank) {
				this.clearInvalid();
				return true;
			} else {
				this.markInvalid(this.blankText);
				return false;
			}
		}
		if (value.length < this.minLength) {
			this.markInvalid(String.format(this.minLengthText, this.minLength));
			return false;
		}
		if (value.length > this.maxLength) {
			this.markInvalid(String.format(this.maxLengthText, this.maxLength));
			return false;
		}
		if (this.vtype) {
			var vt = Ext.form.VTypes;
			if (!vt[this.vtype](value, this)) {
				this.markInvalid(this.vtypeText || vt[this.vtype + 'Text']);
				return false;
			}
		}
		if (typeof this.validator == "function") {
			var msg = this.validator(value);
			if (msg !== true) {
				this.markInvalid(msg);
				return false;
			}
		}
		if (this.regex && !this.regex.test(value)) {
			this.markInvalid(this.regexText);
			return false;
		}
		return true;
	},
	readPropertyValue : function(obj, p) {
		var v = null;
		for (var o in obj) {
			if (o == p)
				return true;
			// v = obj[o];
		}
		return v;
	},
	/**
	 * ����������ֵ���趨��ֵ���������б��е�ĳһ��ֵ���ͻ��Զ�ѡ���Ǹ�ֵ�����û���ҵ�����ͨ�����õ�valueNotFoundText��ʾָ����ֵ��
	 * 
	 * @param {Object}
	 *            obj �趨��ֵ
	 */
	setValue : function(obj) {
		if (!obj) {
			this.clearValue();
			return;
		}
		if (this.fireEvent('beforeSetValue', this, obj) === false) {
			return;
		}
		var v = obj;
		var text = v;
		var value = this.valueField || this.displayField;
		if (typeof v == "object" && this.readPropertyValue(obj, value)) {
			text = obj[this.displayField || this.valueField];
			v = obj[value];
		}
		var node = this.tree.getNodeById(v);
		if (node) {
			text = node.text;
		} else if (this.valueNotFoundText !== undefined) {
			text = this.valueNotFoundText;
		}
		this.lastSelectionText = text;
		if (this.hiddenField) {
			this.hiddenField.value = v;
		}
		TreeComboField.superclass.setValue.call(this, text);
		this.value = v;
		this.text = text;
	},
	/**
	 * �����Ƿ�ɱ༭״̬��
	 * 
	 * @param {Boolean}
	 *            value ���õĿɱ༭״̬��
	 */
	setEditable : function(value) {
		if (value == this.editable) {
			return;
		}
		this.editable = value;
		if (!value) {
			this.el.dom.setAttribute('readOnly', true);
			this.el.on('mousedown', this.onTriggerClick, this);
			this.el.addClass('x-combo-noedit');
		} else {
			this.el.dom.setAttribute('readOnly', false);
			this.el.un('mousedown', this.onTriggerClick, this);
			this.el.removeClass('x-combo-noedit');
		}
	},
	// private
	choice : function(node, eventObject) {
		if (!this.leafOnly || node.isLeaf()) {
			if (node.id != "root") {
				this.setValue(node.id);
			} else {
				this.clearValue();
				this.el.dom.value = node.text;
			}
			this.fireEvent('select', this, node);
			this.collapse();
			this.fireEvent('collapse', this);
		} else {
			if (node.id == "root") {
				this.clearValue();
				this.el.dom.value = node.text;
				this.fireEvent('select', this, node);
				this.collapse();
				this.fireEvent('collapse', this);
			}
		}
	},
	// private
	validateBlur : function() {
		return !this.list || !this.list.isVisible();
	},
	/**
	 * �ж��б��Ƿ���չ��״̬
	 * 
	 * @return {Boolean} �Ƿ���չ��״̬
	 */
	isExpanded : function() {
		return this.list && this.list.isVisible();
	},
	canFocus : function() {
		return !this.disabled;
	},
	onDestroy : function() {
		if (this.tree.rendered && this.list) {
			this.list.hide();
			this.list.destroy();
			delete this.list;
		}
		TreeComboField.superclass.onDestroy.call(this);
	}
});
Ext.reg('treecombo', TreeComboField);

CheckTreeComboField = Ext.extend(TreeComboField, {
	leafOnly : false,// ֻ����ѡ���ӽڵ�
	editable : true,
	// private
	onTriggerClick : function() {
		if (this.disabled)
			return;
		if (!this.listPanel.rendered || !this.list) {
			this.treeId = Ext.id();
			this.list = new Ext.Layer({
						id : this.treeId,
						cls : "x-combo-list",
						constrain : false
					});
			if (!this.innerDom)
				this.innerDom = Ext.getBody().dom;
			if (this.listPanel.rendered) {
				this.list.appendChild(this.listPanel.el);
			} else {
				this.listPanel.render(this.treeId);
				var lw = this.listWidth
						|| Math.max(this.wrap.getWidth(), this.minListWidth);
				this.listPanel.setWidth(lw);
				this.tree.on("expandnode", this.restrictHeight, this);
				this.tree.on("collapsenode", this.restrictHeight, this);
			}
			if (this.value)
				this.setCheckdNode(this.value);
		} else
			this.restrictHeight();
		this.expand();
	},
	// private
	restrictHeight : function() {
		// this.list.dom.style.height = '';
		if (!this.list)
			return;
		var inner = this.innerDom;
		var h = inner.clientHeight - this.wrap.getBottom();
		if (this.listPanel.el.dom.offsetHeight >= h) {
			this.listPanel.setHeight(h);
		} else {
			this.listPanel.setHeight("auto");
			this.tree.setHeight("auto");
		}
	},
	// private
	initComponent : function() {
		CheckTreeComboField.superclass.initComponent.call(this);
		this.listPanel = new Ext.Panel({
					border : false,
					bodyBorder : false,
					buttonAlign : "center",
					layout : "fit",
					items : this.tree,
					bbar : [{
								text : "ȷ��ѡ��",
								handler : this.choice,
								scope : this
							}, {
								text : "���",
								handler : this.cleanChoice,
								scope : this
							}, {
								text : "ȡ��",
								handler : this.cancelChoice,
								scope : this
							}]
				});

	},
	// private
	onRender : function(ct, position) {
		CheckTreeComboField.superclass.onRender.call(this, ct, position);
		this.tree.on("checkchange", this.checkNode, this);

	},
	/**
	 * ���ýڵ��ѡ��״̬ ������ֵ�ַ���������ƥ��ֵ�Ľڵ㶼����Ϊѡ��״̬
	 * 
	 * @param {String}
	 *            v ��Ҫѡ�еĽڵ��ֵ��ɵ��ַ�������ֵ֮��ʹ��','����
	 */
	setCheckdNode : function(v) {
		this.cleanCheckNode(this.tree.root);
		var vs = v.split(",");
		for (var i = 0; i < vs.length; i++) {
			var node = null;
			var valueField = this.valueField;
			this.tree.root.cascade(function(n) {
						if (n.attributes[valueField] == vs[i]) {
							node = n;
							return false;
						}
					});
			if (node)
				this.checkNode(node, true);
		}
	},
	/**
	 * ���ָ���ڵ㼰���ӽڵ��ѡ��״̬
	 * 
	 * @param {Ext.data.Node}
	 *            Ҫ���ѡ��״̬�Ľڵ㡣
	 */
	cleanCheckNode : function(node) {
		var checked = false;
		node.cascade(function(n) {
					if (n.ui.checkbox) {
						n.attributes.checked = n.ui.checkbox.checked = checked;
						n.attributes.selectAll = checked;
						if (checked)
							n.ui.addClass("x-tree-selected");
						else
							n.ui.removeClass("x-tree-selected");
					}
				}, this);
	},
	/**
	 * �ı�ָ���ڵ���ӽڵ�͸��ڵ��ѡ��״̬��<br/>
	 * 
	 * @param {Ext.data.Node}
	 *            node Ҫ�ı�״̬�Ľڵ�
	 * @param {Boolean}
	 *            checked �ı��״̬��trueΪѡ��״̬��falseΪȡ��ѡ��״̬��
	 *            ����ǽ�ָ���ڵ�����Ϊѡ��״̬���÷����Ὣ�ýڵ��ֱ��/��Ӹ��ڵ�����Ϊѡ��״̬���ýڵ��ֱ��/����ӽڵ�����Ϊѡ��״̬<br/>
	 *            ����ǽ�ָ���ڵ�����Ϊȡ��ѡ��״̬���÷����Ὣ�ýڵ��ֱ��/��Ӹ��ڵ㰴�߼�����Ϊȡ��ѡ��״̬���ýڵ��ֱ��/����ӽڵ�����Ϊȡ��ѡ��״̬<br/>
	 * 
	 */
	checkNode : function(node, checked) {
		node.cascade(function(n) {
					if (n.ui.checkbox) {
						n.attributes.checked = n.ui.checkbox.checked = checked;
						n.attributes.selectAll = checked;
						if (checked)
							n.ui.addClass("x-tree-selected");
						else
							n.ui.removeClass("x-tree-selected");
					}
				}, this);
		node.bubble(function(n) {
					if (n != node) {
						if (!checked) {
							n.attributes.selectAll = checked;
							if (n.attributes.checked) {
								n.ui.removeClass("x-tree-selected");
							}
						} else if (!n.attributes.checked) {
							n.attributes.checked = n.ui.checkbox.checked = checked;
						}
					}
				});
	},
	/**
	 * �õ������ֵ
	 * 
	 * @return {String} v ѡ�ж�ֵ�ַ����������ѡ���˶��ֵ�������ɶ��ֵ+','���ӵ��ַ�����
	 */
	getValue : function() {
		return typeof this.value != 'undefined' ? this.value : '';
	},
	// private
	clearValue : function() {
		if (this.hiddenField) {
			this.hiddenField.value = '';
		}
		this.setRawValue('');
		this.lastSelectionText = '';
		this.applyEmptyText();
		if (this.list)
			this.cleanCheckNode(this.tree.root);
	},
	// private
	getNodeValue : function(node) {
		if (node.attributes.selectAll
				&& (!this.leafOnly || node.attributes.leaf)) {
			if (this.t != "")
				this.t += ",";
			if (this.v != "")
				this.v += ",";
			var text = node.attributes[this.displayField || this.valueField];
			if (text === undefined)
				text = node.text;
			var value = node.attributes[this.valueField];
			if (value === undefined)
				value = node.id;
			this.t += text;
			this.v += value;
		} else if (node.attributes.checked)
			node.eachChild(this.getNodeValue, this)
	},
	/**
	 * ����������ֵ���趨��ֵ���������б��е�ĳһ��ֵ���ͻ��Զ�ѡ���Ǹ�ֵ�����û���ҵ�����ͨ�����õ�valueNotFoundText��ʾָ����ֵ��
	 * 
	 * @param {Object}
	 *            obj �趨��ֵ
	 */
	setValue : function(obj) {
		if (!obj) {
			this.clearValue();
			return;
		}
		var v = obj;
		var text = v;
		var value = this.valueField || this.displayField;
		if (typeof v == "object" && this.readPropertyValue(obj, value)) {// ֱ�Ӵ���ֵ

			text = obj[this.displayField || this.valueField];
			v = obj[value];
			if (this.list)
				this.setCheckdNode(v);

		} else {// �Զ��������е�ѡ��ڵ㣬������ֵ
			var root = this.tree.root;
			this.t = "";
			this.v = "";
			if (root.attributes.selectAll) {
				this.t = root.text;
			} else {
				root.eachChild(this.getNodeValue, this);
			}
			text = this.t;
			v = this.v;
		}
		this.lastSelectionText = text;
		if (this.hiddenField) {
			this.hiddenField.value = v;
		}
		TreeComboField.superclass.setValue.call(this, text);
		this.value = v;
		this.text = text;
	},
	/**
	 * ��ѡ�нڵ�����Ӧ����
	 * 
	 * @param {Boolean}
	 *            notClean �Ƿ����֮ǰ�Ѿ�ѡ����
	 */
	choice : function(notClean) {
		if (notClean)
			this.setValue(true);
		else
			this.clearValue();
		this.list.hide();
	},
	/**
	 * �����ǰ�Ѿ�ѡ����
	 */
	cleanChoice : function() {
		this.clearValue();
		this.list.hide();
	},
	/**
	 * ȡ����ǰѡ��
	 */
	cancelChoice : function() {
		this.list.hide();
	},
	onDestroy : function() {
		if (this.listPanel.rendered && this.list) {
			this.list.hide();
			this.list.remove();
		}
		CheckTreeComboField.superclass.onDestroy.call(this);
	}
});
Ext.reg('checktreecombo', CheckTreeComboField);

/**
 * @class SmartCombox
 * @extends Ext.form.ComboBox ��ComboBox������չ,���õ�֧�ֶ����ѡ������ʾ,���ҿ����������б���ֱ��֧���½�����
 * 
 * <pre>
 * <code>
 *  //ʾ��,ria����ṩ�Ĵ���ָ�������ֵ������б�ķ�װ������
 *  getDictionaryCombo : function(name, fieldLabel, sn, valueField,disableBlank, editable) {
 *  return {
 *  xtype : &quot;smartcombo&quot;,
 *  name : name,
 *  hiddenName : name,
 *  displayField : &quot;title&quot;,
 *  valueField : valueField ? valueField : &quot;id&quot;,
 *  lazyRender : true,
 *  triggerAction : &quot;all&quot;,
 *  typeAhead : true,
 *  editable : editable,
 *  allowBlank : !disableBlank,
 *  sn:sn,
 *  objectCreator:{appClass:&quot;SystemDictionaryDetailPanel&quot;,script:&quot;/systemManage/SystemDictionaryManagePanel.js&quot;},
 *  createWinReady:function(win){
 *  if(this.fieldLabel)win.setTitle(&quot;�½�&quot;+this.fieldLabel);
 *  if(this.sn)win.findSomeThing(&quot;parentSn&quot;).setOriginalValue(this.sn);
 *  },
 *  store : new Ext.data.JsonStore({
 *  id : &quot;id&quot;,
 *  url : &quot;systemDictionary.ejf?cmd=getDictionaryBySn&amp;sn=&quot;
 *  + sn,
 *  root : &quot;result&quot;,
 *  totalProperty : &quot;rowCount&quot;,
 *  remoteSort : true,
 *  baseParams : {
 *  pageSize : &quot;-1&quot;
 *  },
 *  fields : [&quot;id&quot;, &quot;title&quot;, &quot;tvalue&quot;]
 *  }),
 *  fieldLabel : fieldLabel
 *  }
 *  },
 * </code>
 * </pre>
 * 
 * @xtype smartcombo
 */
SmartCombox = Ext.extend(Ext.form.ComboBox, {
			/**
			 * @cfg {Boolean} disableCreateObject �Ƿ���������б�����ġ��½����͡�ͬ������ť��
			 */
			/**
			 * ����getValue��ȡ��ֵ���ǻ������ͣ����Ƕ�������
			 * 
			 * @cfg {Boolean} returnObject
			 */
			returnObject : false,

			/**
			 * ���������½�������������
			 * 
			 * @cfg {Object} objectCreator �����ö�������� <url>
			 *      <li>appClass {String} : ���õĴ���ҵ�����Ĺ���ģ������</li>
			 *      <li>script {String} : ���õĴ���ҵ��������ģ���script�ļ���ַ</li>
			 *      </ul>
			 */
			objectCreator : null,
			/**
			 * ������½���ť���򿪴���ҵ�����Ĵ��ں�Ĺ��ӷ���
			 * 
			 * @param {Ext.Window}
			 *            win
			 *            ����ҵ�����Ĵ��ڡ������CrudPanel����ֱ�ӵ���win.getComponent(0)����fp��
			 */
			createWinReady : function(win) {
				if (this.fieldLabel)
					win.setTitle("�½�" + this.fieldLabel);
			},
			/**
			 * �����¶���ʵ���ǵ���Util.addObject������
			 */
			newObject : function() {
				this.collapse();
				var title = this.fieldLabel;
				Util.addObject(this.objectCreator.appClass, this.reload
								.createDelegate(this),
						this.objectCreator.script,
						this.objectCreator.otherScripts, this.createWinReady
								.createDelegate(this));
			},
			/**
			 * ͬ�������б��е�ҵ��������ݡ�
			 */
			synchObject : function() {
				this.store.reload();
			},
			/**
			 * @cfg {Array} operatorButtons
			 *      ��ʾ�������б�ײ��İ�ť���顣�������ͨ�������Ը��ǲ��Զ����Լ�����ʾ��ť��
			 */
			initComponent : function() {
				if (this.objectCreator && !this.disableCreateObject) {
					this.operatorButtons = [{
								text : "�½�",
								iconCls : "add",
								handler : this.newObject,
								scope : this
							}, {
								text : "ͬ��",
								iconCls : "f5",
								handler : this.synchObject,
								scope : this
							}];
				}
				SmartCombox.superclass.initComponent.call(this);
			},
			initList : function() {
				if (!this.list) {
					SmartCombox.superclass.initList.call(this);
					// this.operatorButtons=[{text:"ddd"}];
					if (this.operatorButtons) {// ���ӶԲ�����ť��֧��
						if (this.pageTb) {
							this.pageTb.insert(0, this.operatorButtons);
						} else {
							this.bottomBar = this.list.createChild({
										cls : 'x-combo-list-ft'
									});
							this.bottomToolbar = new Ext.Toolbar(this.operatorButtons);
							this.bottomToolbar.render(this.bottomBar);
							this.assetHeight += this.bottomBar.getHeight();
						}
					}
				}
			},
			/**
			 * ��ȡSmartCombox����ָ��������recordֵ��<br/>
			 * ʵ�ʵĹ��������ȵõ�combox��ֵvalue��Ȼ���������б�󶨵�store�в�ѯfield����value��record������У��򷵻ظ�record��
			 * 
			 * @param {String}
			 *            field ָ����valueField�������ơ�
			 * @return {Object} ����������Record
			 */
			getValueObject : function(field) {
				var val = "";
				if (this.returnObject) {
					val = this.getValue().value;
				} else {
					val = this.getValue();
				}
				if (val) {
					var index = this.store.find(field || "id", val);
					if (index >= 0) {
						var record = this.store.getAt(index);
						return record;
					}
					return null;
				}
				return null;
			},

			/**
			 * ��ȡSmartCombox�е�ֵ
			 * 
			 * @return {Object}
			 */
			getValue : function() {
				if (this.returnObject) {
					var value = this.value;
					if (this.el.dom.value == this.PleaseSelectedValue
							|| this.el.dom.value == this.nullText)
						return null;
					if (this.selectedIndex >= 0) {
						var record = this.store.getAt(this.selectedIndex);
						if (record && record.data) {
							var t = record.data[this.displayField
									|| this.valueField];
							if (t != this.el.dom.value)
								value = null;
						}
					}
					return {
						value : value,
						text : this.el.dom.value,
						toString : function() {
							return this.text;
						}
					};
				} else
					return SmartCombox.superclass.getValue.call(this);
			},
			/**
			 * ����SmartCombox��value
			 * 
			 * @param {String/Object/Number...}
			 *            v Ҫ���õ�ֵ
			 */
			setValue : function(v) {
				if (v && typeof v == "object" && eval("v." + this.valueField)) {
					var value = eval("v." + this.valueField);
					var text = eval("v." + this.displayField) ? eval("v."
							+ this.displayField) : this.valueNotFoundText;
					this.lastSelectionText = text;
					if (this.hiddenField) {
						this.hiddenField.value = value;
					}
					Ext.form.ComboBox.superclass.setValue.call(this, text);
					this.value = value;
					if (this.store.find(this.valueField, value) < 0) {
						var o = {};
						o[this.valueField] = value;
						o[this.displayField] = text;
						if (this.store && this.store.insert) {
							this.store.insert(0, new Ext.data.Record(o));
							// this.select(0);
						}
					}
				} else if (v === null) {
					SmartCombox.superclass.setValue.call(this, "");
				} else {
					SmartCombox.superclass.setValue.call(this, v);
				}
			},
			onSelect : function(record, index) {
				if (this.fireEvent('beforeselect', this, record, index) !== false) {
					this.setValue(record.data[this.valueField
							|| this.displayField]);
					this.collapse();
					this.fireEvent('select', this, record, index);
				}
			}
		});
Ext.reg('smartcombo', SmartCombox);

/**
 * 
 * @class PopupWindowField
 * @extends Ext.form.TriggerField
 *          ��������ѡ��������������������б�Field��ֻ���ڵ���Ա�������ťʱ��ͨ������һ���б��ڣ��ڴ����б��н�������ѡ�񡣣�
 * 
 * <pre>
 * <code>
 *  //ʾ����ѡ����ʹ��PopupWindowField�����б�����ʾ����ϸ�����ݡ�
 *  //���ȴ���һ��GridSelectWin
 *  if(!Global.departmentSelectWin)
 *  Global.departmentSelectWin=new GridSelectWin({
 *  title : &quot;ѡ����&quot;,
 *  width : 540,
 *  height : 400,
 *  layout : &quot;fit&quot;,
 *  buttonAlign : &quot;center&quot;,
 *  closeAction : &quot;hide&quot;,
 *  grid : new DepartmentGrid(),
 *  modal : true,
 *  });
 * 
 *  //��form����
 *  {xtype:'popupwinfield',win:Global.departmentSelectWin,valueField:'id',displayField:'departmentName',returnObject:true},
 * </code>
 * </pre>
 * 
 */
PopupWindowField = Ext.extend(Ext.form.TriggerField, {
			/**
			 * @cfg {Object} win ָ��������win��һ��ʹ��GridSelectWin<br/>
			 */
			win : null,
			/**
			 * @cfg {String} valueField ѡ�е��б�Record��������Ϊfieldֵ��������
			 */
			valueField : "id",
			/**
			 * @cfg {String} displayField ѡ�е��б�Record��������Ϊfield��ʾ��������
			 */
			displayField : "name",
			haveShow : false,
			/**
			 * @cfg {Booealn} editable ��������ѡ����Ƿ��ܱ༭��Ĭ�ϲ���ֱ�ӱ༭
			 */
			editable : false,
			callback : Ext.emptyFn,
			returnObject : false,
			/**
			 * @cfg {Booealn} choiceOnly
			 *      �Ƿ�ֻ��ѡ��ֵ�����Ϊtrue��ֻ����ֵ�����Ϊfalse���ſ���returnObject������
			 */
			choiceOnly : false,
			/**
			 * �õ�ѡ���ֵ<br/> �����choiceOnlyΪtrue,��ֱ�ӷ���this.value<br/>
			 * �����choiceOnlyΪfalse������returnObjectΪtrue����᷵��һ������<br/>
			 * 
			 * @return {Object} ret �õ�ѡ�е�ֵ
			 */
			getValue : function() {
				if (this.choiceOnly)
					return this.value;
				if (this.returnObject)
					return typeof this.value != 'undefined' ? {
						value : this.value,
						text : this.text,
						toString : function() {
							return this.text ? this.text : this.value;
						}
					} : "";
				return typeof this.value != 'undefined' ? this.value : '';
			},
			/**
			 * ���������ֵ��<br/> �����choiceOnlyΪtrue,��ֱ������this.value<br/>
			 * �����choiceOnlyΪfalse������returnObjectΪtrue����ʹ�ô�������o[displayField]����ֵ����Ϊ��ʾ��ֵ��ʹ�ô�������o[valueField]��Ϊֵ<br/>
			 * 
			 * @param {Object}
			 *            v �����Ҫ���õ�ֵ
			 */
			setValue : function(v) {
				if (this.choiceOnly)
					return this.value = v;
				if (v && typeof v == "object" && eval("v." + this.valueField)) {
					var value = eval("v." + this.valueField);
					var text = eval("v." + this.displayField) ? eval("v."
							+ this.displayField) : this.valueNotFoundText;
					this.lastSelectionText = text;
					if (this.hiddenField) {
						this.hiddenField.value = value;
					}
					PopupWindowField.superclass.setValue.call(this, text);
					this.value = value;
					this.text = text;
				} else if (v === null)
					PopupWindowField.superclass.setValue.call(this, "");
				else
					PopupWindowField.superclass.setValue.call(this, v);
			},
			/**
			 * ����������������ť������ѡ�񴰿�
			 */
			onTriggerClick : function() {
				if (this.win) {
					this.win.show();
				}
			},
			onRender : function(ct, position) {
				PopupWindowField.superclass.onRender.call(this, ct, position);
				if (this.win) {
					this.win.on("select", this.choice, this);
				}
				if (this.hiddenName) {
					this.hiddenField = this.el.insertSibling({
								tag : 'input',
								type : 'hidden',
								name : this.hiddenName,
								id : (this.hiddenId || this.hiddenName)
							}, 'before', true);
					this.hiddenField.value = this.hiddenValue !== undefined
							? this.hiddenValue
							: this.value !== undefined ? this.value : '';
					this.el.dom.removeAttribute('name');
				}
				if (!this.editable) {
					this.editable = true;
					this.setEditable(false);
				}
				if (this.choiceOnly)
					this.el.hide();
			},
			/**
			 * �ڵ����б�����ѡ����ֵ֮��ִ�еķ���<br/> Ĭ����ֱ�����ø�field��ֵ
			 * 
			 * @param {Object}
			 *            data �����������б�ѡ��Record��Ӧ��dataֵ
			 * @param {Object}
			 *            win �������б���ʵ��
			 */
			choice : function(data, win) {
				this.setValue(data);
				this.fireEvent('select', data, win);
			},
			/**
			 * @event select ���ڵ��������б���ѡ����ĳ�����ݣ���ִ�����choice�������׳�<br/>
			 * @param {Object}
			 *            data �����������б�ѡ��Record��Ӧ��dataֵ
			 * @param {Object}
			 *            win �������б���ʵ��
			 */
			initComponent : function() {
				PopupWindowField.superclass.initComponent.call(this);
				this.addEvents("select");

			},
			validateBlur : function() {
				return !this.win || !this.win.isVisible();
			},
			onDestroy : function() {
				if (this.win && this.win.isVisible()) {
					this.win.hide();
				}
				PopupWindowField.superclass.onDestroy.call(this);
			},
			/**
			 * ����Ϊ���Ա༭
			 * 
			 * @param {Boolean}
			 *            value
			 */
			setEditable : function(value) {
				if (value == this.editable) {
					return;
				}
				this.editable = value;
				if (!value) {
					this.el.dom.setAttribute('readOnly', true);
					this.el.on("dblclick", this.onTriggerClick, this);
					this.el.on("click", this.onTriggerClick, this);
					this.el.addClass('x-combo-noedit');
				} else {
					this.el.dom.setAttribute('readOnly', false);
					this.el.un('mousedown', this.onTriggerClick, this);
					this.el.removeClass('x-combo-noedit');
				}
			}
		});
Ext.reg('popupwinfield', PopupWindowField);

/**
 * @class GridSelectWin
 * @extends Ext.Window
 *          ����һ��Window���û�ѡ��grid�е����ݣ�һ���PopupWindowField���ʹ�ã���ΪPopupWindowField�е�win���ԡ�
 *          ʾ���ο�PopupWindowField��ʾ��
 */
GridSelectWin = Ext.extend(Ext.Window, {
			/**
			 * @cfg {String} title ���ڵ����ƣ�Ĭ��Ϊ"ѡ������"
			 */
			title : "ѡ������",
			/**
			 * @cfg {Integer} width ���ڿ�ȣ�Ĭ��Ϊ540
			 */
			width : 540,
			/**
			 * @cfg {Integer} height ���ڸ߶ȣ�Ĭ��Ϊ400
			 */
			height : 400,
			/**
			 * @cfg {String} layout ���ڲ��֣�Ĭ��Ϊfit
			 */
			layout : "fit",
			/**
			 * @cfg {String} buttonAlign �����·���ť�Ķ��䷽ʽ��Ĭ��Ϊcenter
			 */
			buttonAlign : "center",
			/**
			 * @cfg {String} closeAction ���ڹرշ�ʽ��Ĭ��Ϊhide
			 */
			closeAction : "hide",
			/**
			 * @cfg {Object} grid ��������ʾ���б���󡣱�����һ��Ext.grid.GridPanel������̳���ʵ����<br/>
			 *      ����Ҫ�Ĳ���
			 */
			grid : null,// grid�Ǳ��봫�ݵĶ���
			/**
			 * @cfg {Booelan} modal �Ƿ���ģʽ����
			 */
			modal : true,
			callback : Ext.emptyFn,
			/**
			 * @event select �����б���ѡ����һ�л��߶��к���׳��¼�
			 * @param {Array}
			 *            datas ѡ�е�record��data����
			 */
			/**
			 * �����б���ѡ����һ�л��߶��к�Ĵ����¼���<br/>
			 * Ĭ�϶����ǽ�ѡ��record��data����һ�������У��رյ�ǰ���ڣ���������ͨ��select�¼��׳���<br/>
			 */
			choice : function() {
				var grid = this.grid.grid || this.grid;
				var records = grid.getSelectionModel().getSelections();
				if (!records || records.length < 1) {
					Ext.Msg.alert("$!{lang.get('Prompt')}",
							"$!{lang.get('Select first')}");
					return false;
				}
				var datas = [];
				for (var i = 0; i < records.length; i++) {
					datas[i] = records[i].data;
				}
				this.hide();
				this.fireEvent('select', datas, this);
			},
			initComponent : function() {
				this.buttons = [{
							text : "ȷ��",
							handler : this.choice,
							scope : this
						}, {
							text : "ȡ��",
							handler : function() {
								this.hide();
							},
							scope : this
						}];
				GridSelectWin.superclass.initComponent.call(this);
				if (this.grid) {
					var grid = this.grid.grid || this.grid;// ����BaseGridList����
					grid.on("rowdblclick", this.choice, this);
					this.add(this.grid);
				}
				this.addEvents("select");
			}
		});
/**
 * @class Ext.ux.TreeCheckNodeUI
 * @extends Ext.tree.TreeNodeUI
 *
 * �� Ext.tree.TreeNodeUI ����checkbox���ܵ���չ,��̨���صĽ����Ϣ���÷�Ҫ����checked����
 *
 * ��չ�Ĺ��ܵ��У�
 * һ��֧��ֻ������Ҷ�ӽ���ѡ��
 *    ֻ�е����ص����������leaf = true ʱ��������checkbox��ѡ
 *    ʹ��ʱ��ֻ����������ʱ���������� onlyLeafCheckable: true �ȿɣ�Ĭ����false
 *
 * ����֧�ֶ����ĵ�ѡ
 *    ֻ����ѡ��һ�����
 *    ʹ��ʱ��ֻ����������ʱ���������� checkModel: "single" �ȿ�
 *
 * ����֧�ֶ����ļ�����ѡ
 *    ��ѡ����ʱ���Զ�ѡ��ý���µ������ӽ�㣬��ý������и���㣨�������⣩���ر���֧���첽�����ӽ�㻹û��ʾʱ����Ӻ�̨ȡ���ӽ�㣬Ȼ����ѡ��/ȡ��ѡ��
 *    ʹ��ʱ��ֻ����������ʱ���������� checkModel: "cascade" ��"parentCascade"��"childCascade"�ȿ�
 *
 * �������"check"�¼�
 *    ���¼�����������checkbox�����ı�ʱ����
 *    ʹ��ʱ��ֻ�����ע���¼�,�磺
 *    tree.on("check",function(node,checked){...});
 *
 * Ĭ������£�checkModelΪ'multiple'��Ҳ���Ƕ�ѡ��onlyLeafCheckableΪfalse�����н�㶼��ѡ
 *
 * ʹ�÷�������loader����� baseAttrs:{uiProvider:Ext.ux.TreeCheckNodeUI} �ȿ�.
 * ���磺
 *   var tree = new Ext.tree.TreePanel({
 *   el:'tree-ct',
 *   width:568,
 *   height:300,
 *   checkModel: 'cascade',   //�����ļ�����ѡ
 *   onlyLeafCheckable: false,//�������н�㶼��ѡ
 *   animate: false,
 *   rootVisible: false,
 *   autoScroll:true,
 *   loader: new Ext.tree.DWRTreeLoader({
 *    dwrCall:Tmplt.getTmpltTree,
 *    baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //��� uiProvider ����
 *   }),
 *   root: new Ext.tree.AsyncTreeNode({ id:'0' })
 * });
 * tree.on("check",function(node,checked){alert(node.text+" = "+checked)}); //ע��"check"�¼�
 * tree.render();
 *
 */

Ext.ux.TreeCheckNodeUI = function() {
	//��ѡ: 'multiple'(Ĭ��)
	//��ѡ: 'single'
	//������ѡ: 'cascade'(ͬʱѡ������);'parentCascade'(ѡ��);'childCascade'(ѡ��)
	this.checkModel = 'multiple';

	//only leaf can checked
	this.onlyLeafCheckable = false;

	Ext.ux.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.TreeCheckNodeUI, Ext.tree.TreeNodeUI, {

	renderElements : function(n, a, targetNode, bulkRender) {
		var tree = n.getOwnerTree();
		this.checkModel = tree.checkModel || this.checkModel;
		this.onlyLeafCheckable = tree.onlyLeafCheckable || false;

		// add some indent caching, this helps performance when rendering a large tree
		this.indentMarkup = n.parentNode
				? n.parentNode.ui.getChildIndent()
				: '';

		//var cb = typeof a.checked == 'boolean';
		var cb = (!this.onlyLeafCheckable || a.leaf);
		var href = a.href ? a.href : Ext.isGecko ? "" : "#";
		var buf = [
				'<li class="x-tree-node"><div ext:tree-node-id="',
				n.id,
				'" class="x-tree-node-el x-tree-node-leaf x-unselectable ',
				a.cls,
				'" unselectable="on">',
				'<span class="x-tree-node-indent">',
				this.indentMarkup,
				"</span>",
				'<img src="',
				this.emptyIcon,
				'" class="x-tree-ec-icon x-tree-elbow" />',
				'<img src="',
				a.icon || this.emptyIcon,
				'" class="x-tree-node-icon',
				(a.icon ? " x-tree-node-inline-icon" : ""),
				(a.iconCls ? " " + a.iconCls : ""),
				'" unselectable="on" />',
				cb
						? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked
								? 'checked="checked" />'
								: '/>'))
						: '',
				'<a hidefocus="on" class="x-tree-node-anchor" href="', href,
				'" tabIndex="1" ',
				a.hrefTarget ? ' target="' + a.hrefTarget + '"' : "",
				'><span unselectable="on">', n.text, "</span></a></div>",
				'<ul class="x-tree-node-ct" style="display:none;"></ul>',
				"</li>"].join('');

		var nel;
		if (bulkRender !== true && n.nextSibling
				&& (nel = n.nextSibling.ui.getEl())) {
			this.wrap = Ext.DomHelper.insertHtml("beforeBegin", nel, buf);
		} else {
			this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf);
		}

		this.elNode = this.wrap.childNodes[0];
		this.ctNode = this.wrap.childNodes[1];
		var cs = this.elNode.childNodes;
		this.indentNode = cs[0];
		this.ecNode = cs[1];
		this.iconNode = cs[2];
		var index = 3;
		if (cb) {
			this.checkbox = cs[3];
			Ext.fly(this.checkbox).on('click',
					this.check.createDelegate(this, [null]));
			index++;
		}
		this.anchor = cs[index];
		this.textNode = cs[index].firstChild;
	},

	// private
	check : function(checked) {
		var n = this.node;
		var tree = n.getOwnerTree();
		this.checkModel = tree.checkModel || this.checkModel;

		if (checked === null) {
			checked = this.checkbox.checked;
		} else {
			this.checkbox.checked = checked;
		}

		n.attributes.checked = checked;
		tree.fireEvent('check', n, checked);

		if (this.checkModel == 'single') {
			var checkedNodes = tree.getChecked();
			for (var i = 0; i < checkedNodes.length; i++) {
				var node = checkedNodes[i];
				if (node.id != n.id) {
					node.getUI().checkbox.checked = false;
					node.attributes.checked = false;
					tree.fireEvent('check', node, false);
				}
			}
		} else if (!this.onlyLeafCheckable) {
			if (this.checkModel == 'cascade'
					|| this.checkModel == 'parentCascade') {
				var parentNode = n.parentNode;
				if (parentNode !== null) {
					this.parentCheck(parentNode, checked);
				}
			}
			if (this.checkModel == 'cascade'
					|| this.checkModel == 'childCascade') {
				if (!n.expanded && !n.childrenRendered) {
					n.expand(false, false, this.childCheck);
				} else {
					this.childCheck(n);
				}
			}
		}
	},

	// private
	childCheck : function(node) {
		var a = node.attributes;
		if (!a.leaf) {
			var cs = node.childNodes;
			var csui;
			for (var i = 0; i < cs.length; i++) {
				csui = cs[i].getUI();
				if (csui.checkbox.checked ^ a.checked)
					csui.check(a.checked);
			}
		}
	},

	// private
	parentCheck : function(node, checked) {
		var checkbox = node.getUI().checkbox;
		if (typeof checkbox == 'undefined')
			return;
		if (!(checked ^ checkbox.checked))
			return;
		if (!checked && this.childHasChecked(node))
			return;
		checkbox.checked = checked;
		node.attributes.checked = checked;
		node.getOwnerTree().fireEvent('check', node, checked);

		var parentNode = node.parentNode;
		if (parentNode !== null) {
			this.parentCheck(parentNode, checked);
		}
	},

	// private
	childHasChecked : function(node) {
		var childNodes = node.childNodes;
		if (childNodes || childNodes.length > 0) {
			for (var i = 0; i < childNodes.length; i++) {
				if (childNodes[i].getUI().checkbox.checked)
					return true;
			}
		}
		return false;
	},

	toggleCheck : function(value) {
		var cb = this.checkbox;
		if (cb) {
			var checked = (value === undefined ? !cb.checked : value);
			this.check(checked);
		}
	}
});