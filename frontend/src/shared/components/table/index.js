import React from 'react';
import Table from 'react-bootstrap/Table';
import './style.css';

//NON PAGINATED TABLE
export default function NonPaginatedTable ({headings, data, hover= true, onClick}) {
    return (
        <Table responsive hover={hover}>
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
                        <tr key={element.position} onClick={() => onClick(element)}>
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
    )
}