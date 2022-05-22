import React, {useState} from 'react';
import Pagination from 'react-bootstrap/Pagination';
import { DOTS, DOTSL, DOTSR, usePagination } from '../../hooks/paginationHook';



export function PaginationComponent({
        onPageChange,
        totalCount,
        siblingCount = 1,
        currentPage,
        pageSize,
    }) {
    const paginationRange = usePagination({
        currentPage,
        totalCount,
        siblingCount,
        pageSize
    });

    const onNext = async () => {
        onPageChange(currentPage + 1);
    };

    const onPrevious = async () => {
        onPageChange(currentPage - 1);
    };

    let lastPage = paginationRange[paginationRange.length - 1];

    return (
        <>
            <Pagination>
                <Pagination.Prev key={0} onClick={onPrevious} disabled={currentPage==1} />
                {paginationRange.map(pageNumber => {
        
                    // If the pageItem is a DOT, render the DOTS unicode character
                    if (pageNumber === DOTSL || pageNumber === DOTSR) {
                        return <Pagination.Ellipsis key={pageNumber}/>;
                    }
                    
                    return (
                        <Pagination.Item key={pageNumber} active={pageNumber === currentPage} onClick={async () => await onPageChange(pageNumber)}>
                            {pageNumber}
                        </Pagination.Item>
                    );
                })}
                <Pagination.Next key={lastPage+1} onClick={onNext} disabled={currentPage==lastPage}/>
            </Pagination>
        </>    
    );
}

export default PaginationComponent;