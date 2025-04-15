import React from 'react'
import { CPagination, CPaginationItem } from '@coreui/react'

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  return (
    <CPagination align="end" aria-label="Page navigation example" style={{ cursor: 'pointer' }}>
      <CPaginationItem
        aria-label="Previous"
        disabled={currentPage === 0}
        onClick={() => onPageChange(currentPage - 1)}
      >
        <span aria-hidden="true">&laquo;</span>
      </CPaginationItem>

      {[...Array(totalPages)].map((_, index) => (
        <CPaginationItem
          key={index}
          active={currentPage === index}
          onClick={() => onPageChange(index)}
        >
          {index + 1}
        </CPaginationItem>
      ))}
      <CPaginationItem
        aria-label="Next"
        disabled={currentPage === totalPages - 1}
        onClick={() => onPageChange(currentPage + 1)}
      >
        <span aria-hidden="true">&raquo;</span>
      </CPaginationItem>
    </CPagination>
  )
}

export default Pagination
