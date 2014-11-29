/** JTABLE Multiple toolbar search extension 

**/
(function ($) {
	var base={
		_addRowToTableHead:$.hik.jtable.prototype._addRowToTableHead
	}
    $.extend(true, $.hik.jtable.prototype, {
		options: {
			toolbarsearch:false
		},
		/** Overrides Method
		/* Adds tr element to given thead element
        *************************************************************************/
        _addRowToTableHead: function ($thead) {
            var $tr = $('<tr></tr>')
                .appendTo($thead);

            this._addColumnsToHeaderRow($tr);
			if(this.options.toolbarsearch){			
	            var $tr = $('<tr></tr>')
                .appendTo($thead);
    	        this._toolbarsearch_addColumnsToHeaderRow($tr);
			}

		},
		/* Adds column header cells to given tr element.
        *************************************************************************/
        _toolbarsearch_addColumnsToHeaderRow: function ($tr) {
			var self = this;
	    	for (var i = 0; i < this._columnList.length; i++) {
    	    	var fieldName = this._columnList[i];
        	    var $headerCell = this._toolbarsearch_createHeaderCellForField(fieldName, this.options.fields[fieldName]);
            	$headerCell.appendTo($tr);
            }
	    	/*
			$reset = $("<th></th>")
                .addClass('jtable-toolbarsearch-reset')
                .attr('colspan',$(".jtable-command-column-header").length);
			$resetbutton = $('<input type="button" value="Reset"/>').appendTo($reset);
			$tr.append($reset);
			*/
			$reset = $('<th></th>')
            .addClass('jtable-toolbarsearch-reset')
            .attr('colspan',$(".jtable-command-column-header").length);
			$resetbutton = $('<input type="button" value="Reset"/>').appendTo($reset);
			$resetbutton.click(function(){
			$('.jtable-toolbarsearch').val('');
			self.load({});				
			});
			$tr.append($reset);
        },		

        /* Creates a header cell for given field.
        *  Returns th jQuery object.
        *************************************************************************/		
        _toolbarsearch_createHeaderCellForField: function (fieldName, field) {
			var self = this;
			if(typeof field.searchable === 'undefined'){
				field.searchable = true;
			};
            field.width = field.width || '10%'; //default column width: 10%.
			var $input = this._getColumnDisplay(fieldName,field);									
            var $headerContainerDiv = $('<div />')
                .addClass('jtable-column-header-container');
                
			if(field.searchable){	
				$headerContainerDiv.append($input);
			}
            var $th = $('<th></th>')
                .addClass('jtable-column-header')
                .css('width', field.width)
                .data('fieldName', fieldName)
                .append($headerContainerDiv);
            return $th;		

        },
		_getColumnDisplay:function(fieldName,field){
			if (field.type == 'date') {
				return this._getDisplayDate(fieldName,field);
			} else if (field.type == 'checkbox') {
                return this._getDisplayCheckBox(fieldName,field);
            } else if (field.options) { //combobox or radio button list since there are options.
                return this._getDisplayComboBox(fieldName,field);
            } else {
				return this._getDisplayText(fieldName,field);
            }
		},
		_getDisplayDate:function(fieldName,field){
			var displayFormat = field.displayFormat || this.options.defaultDateFormat;
			var $input = $('<input id="jtable-toolbarsearch-' + fieldName + '" type="text"/>')
			.addClass('jtable-toolbarsearch')
			.css('width','90%')
			.datepicker({dateFormat:displayFormat});
			return $input;
		},
		_getDisplayText:function(fieldName,field){
			var $input = $('<input id="jtable-toolbarsearch-' + fieldName + '" type="text"/>')
			.addClass('jtable-toolbarsearch')
			.css('width','90%');
			return $input;
		},
		_getDisplayCheckBox:function(fieldName,field){
			var $input = $('<input id="jtable-toolbarsearch-' + fieldName + '" type="text"/>')
			.addClass('jtable-toolbarsearch')
			.css('width','90%')
			return $input;
		},
		_getDisplayComboBox:function(fieldName,field){
			
			var $input = $('<select id="jtable-toolbarsearch-' + fieldName + '"></select>')
			.addClass('jtable-toolbarsearch')
			.css('width','90%')
		//	var $input = $('<input id="jtable-toolbarsearch-' + fieldName + '" type="text"/>')
			
			for (var option in field.options) {
				console.log(field.options[option]);
                $input.append('<option value="' + option + '">' + field.options[option] + '</option>');
			}
			
			return $input;
		}

		
	});
	
})(jQuery);
