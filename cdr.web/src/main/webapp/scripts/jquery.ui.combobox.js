(function($) {
    $.widget("ui.combobox", {
        options: {
            width: 120,
            imageUrl: "../images/dropdown.gif",
            showButton: true
        },

        _create: function() {
            var input,
					self = this,
					select = this.element.hide(),
					selected = select.children(":selected"),
					value = selected.val() ? selected.text() : "",
					wrapper = this.wrapper = $("<span>")
						.addClass("ui-combobox")
						.insertAfter(select);

            input = $("<input>")
					.appendTo(wrapper)
					.val(value)
    				.css({ width: this.options.width })
    				.addClass("ui-combobox-state-default ui-combobox-input")
					.bind("focus", function() {
					    $(this).val("");
					    //selected = select.children(":selected");
					    //$(this).val(selected[0].searchtext);
					})
					.bind("blur", function() {
					    selected = select.children(":selected");
					    $(this).val(selected[0].text);
					})
					.bind("mouseover", function() {
					    this.style.background = '#FDE8FE';
					})
					.bind("mouseout", function() {
					    this.style.background = '#FFFFFF';
					})
					.autocomplete({
					    delay: 0,
					    minLength: 0,
					    source: function(request, response) {
					        var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					        var comboData = select.children("option").map(function() {
					            var text = this.text;
					            if (this.value) {
					                searchText = this.searchtext ? this.searchtext : this.text;
					                if (!request.term || matcher.test(searchText) || matcher.test(this.text)) {
					                    return {
					                        label: this.text,
					                        value: this.text,
					                        option: this
					                    };
					                }
					            }
					        });

					        response(comboData);
					    },
					    select: function(event, ui) {
					        ui.item.option.selected = true;
					        self._trigger("selected", event, {
					            item: ui.item.option
					        });
					        //选中之后，让input失去焦点
					        $(this).blur();
					    },

					    change: function(event, ui) {
					        if (!ui.item) {
					            var matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex($(this).val()) + "$", "i"),
									valid = false;
					            select.children("option").each(function() {
					                if ($(this).text().match(matcher)) {
					                    this.selected = valid = true;
					                    return false;
					                }
					            });
					            if (!valid) {
					                // remove invalid value, as it didn't match anything
					                $(this).val("");
					                select.val("");
					                input.data("autocomplete").term = "";
					                return false;
					            }
					        }
					    }
					});

            input.data("autocomplete")._renderItem = function(ul, item) {
                return $("<li></li>")
						.data("item.autocomplete", item)
						.append("<a>" + item.label + "</a>")
						.appendTo(ul);
            };

            button = $("<img src='" + this.options.imageUrl + "'/>")
				.attr("tabIndex", -1)
				.insertAfter(input)
				.addClass("ui-combobox-button")
				.css({ height: input.height() + 4 })
				.click(function() {
				    // close if already visible
				    if (input.autocomplete("widget").is(":visible")) {
				        input.autocomplete("close");
				        return;
				    }

				    // work around a bug (likely same cause as #5265)
				    $(this).blur();
				    // pass empty string as value to search for, displaying all results
				    input.autocomplete("search", "");
				    //input.focus();
				});

            if (!this.options.showButton)
                button.css("display", "none");
            input.width(input.width() - button.width());
        },

        destroy: function() {
            this.wrapper.remove();
            this.element.show();
            $.Widget.prototype.destroy.call(this);
        }
    });
})(jQuery);
