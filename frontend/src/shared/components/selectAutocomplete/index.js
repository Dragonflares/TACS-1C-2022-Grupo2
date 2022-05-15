import React, { useCallback, useState, useEffect } from "react";

import { AsyncTypeahead } from 'react-bootstrap-typeahead';

const getValidationObject = (validated, valid) => {
    return  validated ? {
        isValid:valid,
        isInvalid:!valid
    }
    : {}
}

export default function SearchMultipleAutocomplete({validated = false, valid = null, placeholder = null, dataFormater , onSelection, onSearch, required = false, className = ''}) {
    
    const [isLoading, setIsLoading] = useState(false);
    const [elements, setElements] = useState([]);
    const [search, setSearch] = useState('');
    const [isvalidated, setValidate] = useState(getValidationObject(validated, valid));

    const handleSearch = useCallback((s) => {
        setSearch(s);

        setIsLoading(true);

        onSearch(s).then((response) => {            
            setIsLoading(false);
            setElements(response.data)
        });
    });

    useEffect(() => {
        setValidate(getValidationObject(validated, valid))
        
        console.log(valid);
    }, [valid, validated])
    
    const handleSelection = useCallback((selections) => {
        onSelection(selections);
    });

    return (
        <AsyncTypeahead
            id="search-autocomplete"
            className={className}
            filterBy={() => (true)}
            isLoading={isLoading}
            minLength={3}
            onSearch={handleSearch}
            options={elements}
            onChange={handleSelection}
            placeholder={placeholder}
            labelKey={dataFormater}
            multiple
            required={required}
            useCache={false}
            {...isvalidated}
        />
    )
}