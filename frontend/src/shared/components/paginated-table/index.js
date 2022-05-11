import React, {useState, useCallback} from 'react';
import Table from 'react-bootstrap/Table';
import PaginationComponent from '../pagination';
import './style.css';

export function PaginatedTable ({headings, data, pageSize = 10, handlePageChange, onClick}) {

    const [page, setPage] = useState(1);

    const pageChangeHandler = useCallback(
        async (newPage) => {
            if(newPage !== page){
                setPage(newPage);

                await handlePageChange(newPage, pageSize);        
            }    
        }
    );

    return (
        <>
            <Table responsive hover>
                <thead>
                    <tr key={0}>
                    {Array.from(headings).map((header, index) => {
                        return (
                            <th key={index}>{header.show}</th>
                        )
                    })}
                    </tr>
                </thead>
                <tbody>
                    {Array.from(data.elements).map((element) => {
                         return (
                            <tr key={element.id} onClick={() => onClick(element)}>
                                {
                                    Array.from(headings).map((header, index) => {
                                        return (
                                            <td key={index}>
                                                {element[header.name]}
                                            </td>
                                        )
                                    })
                                }
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
            <PaginationComponent currentPage={page} onPageChange={pageChangeHandler} pageSize={pageSize} 
                totalCount={data.count} siblingCount={2}/>
        </>
    );
}