/**
 * School Leavers System - Main JavaScript
 * 
 * This file contains common JavaScript functionality for the application
 * including form validation, UI interactions, and utility functions.
 * 
 * @author CCT Student
 * @version 1.0
 */

// ===========================================
// GLOBAL VARIABLES
// ===========================================
const APP_CONFIG = {
    apiBaseUrl: '/api',
    pageSize: 10,
    dateFormat: 'YYYY-MM-DD',
    currency: 'EUR'
};

// ===========================================
// UTILITY FUNCTIONS
// ===========================================

/**
 * Show a toast notification
 */
function showToast(message, type = 'info', duration = 3000) {
    const toastContainer = document.getElementById('toast-container') || createToastContainer();
    
    const toast = document.createElement('div');
    toast.className = `toast toast-${type} fade-in`;
    toast.innerHTML = `
        <div class="toast-header">
            <i class="fas fa-${getToastIcon(type)} me-2"></i>
            <strong class="me-auto">${getToastTitle(type)}</strong>
            <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
        </div>
        <div class="toast-body">
            ${message}
        </div>
    `;
    
    toastContainer.appendChild(toast);
    
    // Auto remove after duration
    setTimeout(() => {
        if (toast.parentElement) {
            toast.remove();
        }
    }, duration);
}

/**
 * Create toast container if it doesn't exist
 */
function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container position-fixed top-0 end-0 p-3';
    container.style.zIndex = '9999';
    document.body.appendChild(container);
    return container;
}

/**
 * Get toast icon based on type
 */
function getToastIcon(type) {
    const icons = {
        success: 'check-circle',
        error: 'exclamation-circle',
        warning: 'exclamation-triangle',
        info: 'info-circle'
    };
    return icons[type] || 'info-circle';
}

/**
 * Get toast title based on type
 */
function getToastTitle(type) {
    const titles = {
        success: 'Success',
        error: 'Error',
        warning: 'Warning',
        info: 'Information'
    };
    return titles[type] || 'Information';
}

/**
 * Format number with commas
 */
function formatNumber(number) {
    return new Intl.NumberFormat().format(number);
}

/**
 * Format currency
 */
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IE', {
        style: 'currency',
        currency: APP_CONFIG.currency
    }).format(amount);
}

/**
 * Format date
 */
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IE');
}

/**
 * Validate email format
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Validate quarter format (Q1YYYY, Q2YYYY, Q3YYYY, Q4YYYY)
 */
function isValidQuarter(quarter) {
    const quarterRegex = /^[Q][1-4]\d{4}$/;
    return quarterRegex.test(quarter);
}

/**
 * Validate statistic code format
 */
function isValidStatisticCode(code) {
    const codeRegex = /^[A-Z0-9]+$/;
    return codeRegex.test(code);
}

// ===========================================
// FORM VALIDATION
// ===========================================

/**
 * Validate school leaver form
 */
function validateSchoolLeaverForm(form) {
    const errors = [];
    
    // Validate statistic code
    const statisticCode = form.querySelector('[name="statisticCode"]').value.trim();
    if (!statisticCode) {
        errors.push('Statistic code is required');
    } else if (!isValidStatisticCode(statisticCode)) {
        errors.push('Statistic code must contain only uppercase letters and numbers');
    }
    
    // Validate statistic label
    const statisticLabel = form.querySelector('[name="statisticLabel"]').value.trim();
    if (!statisticLabel) {
        errors.push('Statistic label is required');
    } else if (statisticLabel.length > 100) {
        errors.push('Statistic label must not exceed 100 characters');
    }
    
    // Validate quarter
    const quarter = form.querySelector('[name="quarter"]').value.trim();
    if (!quarter) {
        errors.push('Quarter is required');
    } else if (!isValidQuarter(quarter)) {
        errors.push('Quarter must be in format Q1YYYY, Q2YYYY, Q3YYYY, or Q4YYYY');
    }
    
    // Validate sex
    const sex = form.querySelector('[name="sex"]').value.trim();
    if (!sex) {
        errors.push('Sex is required');
    }
    
    // Validate unit
    const unit = form.querySelector('[name="unit"]').value.trim();
    if (!unit) {
        errors.push('Unit is required');
    }
    
    // Validate value
    const value = form.querySelector('[name="value"]').value;
    if (!value) {
        errors.push('Value is required');
    } else {
        const numValue = parseFloat(value);
        if (isNaN(numValue) || numValue < 0 || numValue > 999.99) {
            errors.push('Value must be a number between 0 and 999.99');
        }
    }
    
    return errors;
}

/**
 * Show form validation errors
 */
function showFormErrors(errors) {
    // Clear previous errors
    clearFormErrors();
    
    // Show errors
    errors.forEach(error => {
        showToast(error, 'error');
    });
}

/**
 * Clear form validation errors
 */
function clearFormErrors() {
    const errorElements = document.querySelectorAll('.error-message');
    errorElements.forEach(element => element.remove());
}

// ===========================================
// UI INTERACTIONS
// ===========================================

/**
 * Initialize tooltips
 */
function initTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

/**
 * Initialize popovers
 */
function initPopovers() {
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
}

/**
 * Show loading spinner
 */
function showLoading(element) {
    if (typeof element === 'string') {
        element = document.querySelector(element);
    }
    
    if (element) {
        element.innerHTML = '<div class="spinner"></div>';
        element.disabled = true;
    }
}

/**
 * Hide loading spinner
 */
function hideLoading(element) {
    if (typeof element === 'string') {
        element = document.querySelector(element);
    }
    
    if (element) {
        element.disabled = false;
    }
}

/**
 * Toggle password visibility
 */
function togglePasswordVisibility(inputId, buttonId) {
    const input = document.getElementById(inputId);
    const button = document.getElementById(buttonId);
    
    if (input.type === 'password') {
        input.type = 'text';
        button.innerHTML = '<i class="fas fa-eye-slash"></i>';
    } else {
        input.type = 'password';
        button.innerHTML = '<i class="fas fa-eye"></i>';
    }
}

// ===========================================
// TABLE FUNCTIONS
// ===========================================

/**
 * Initialize data table
 */
function initDataTable(tableId, options = {}) {
    const table = document.getElementById(tableId);
    if (!table) return;
    
    // Add responsive wrapper
    if (!table.parentElement.classList.contains('table-responsive')) {
        table.parentElement.classList.add('table-responsive');
    }
    
    // Add hover effects
    table.classList.add('table-hover');
    
    // Initialize sorting if enabled
    if (options.sortable !== false) {
        initTableSorting(table);
    }
}

/**
 * Initialize table sorting
 */
function initTableSorting(table) {
    const headers = table.querySelectorAll('th[data-sort]');
    
    headers.forEach(header => {
        header.style.cursor = 'pointer';
        header.addEventListener('click', () => {
            const column = header.getAttribute('data-sort');
            const direction = header.getAttribute('data-direction') === 'asc' ? 'desc' : 'asc';
            
            // Update all headers
            headers.forEach(h => h.setAttribute('data-direction', ''));
            header.setAttribute('data-direction', direction);
            
            // Sort table
            sortTable(table, column, direction);
        });
    });
}

/**
 * Sort table by column
 */
function sortTable(table, column, direction) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    rows.sort((a, b) => {
        const aValue = a.querySelector(`td[data-${column}]`).textContent;
        const bValue = b.querySelector(`td[data-${column}]`).textContent;
        
        if (direction === 'asc') {
            return aValue.localeCompare(bValue);
        } else {
            return bValue.localeCompare(aValue);
        }
    });
    
    // Reorder rows
    rows.forEach(row => tbody.appendChild(row));
}

// ===========================================
// AJAX FUNCTIONS
// ===========================================

/**
 * Make AJAX request
 */
async function makeRequest(url, options = {}) {
    const defaultOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'same-origin'
    };
    
    const requestOptions = { ...defaultOptions, ...options };
    
    try {
        const response = await fetch(url, requestOptions);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    } catch (error) {
        console.error('Request failed:', error);
        throw error;
    }
}

/**
 * Delete record with confirmation
 */
async function deleteRecord(url, recordName = 'record') {
    if (!confirm(`Are you sure you want to delete this ${recordName}?`)) {
        return false;
    }
    
    try {
        const response = await makeRequest(url, { method: 'DELETE' });
        showToast(`${recordName} deleted successfully`, 'success');
        return true;
    } catch (error) {
        showToast(`Failed to delete ${recordName}`, 'error');
        return false;
    }
}

// ===========================================
// EVENT LISTENERS
// ===========================================

/**
 * Initialize all event listeners
 */
function initEventListeners() {
    // Form submission
    document.addEventListener('submit', function(e) {
        const form = e.target;
        
        // Validate school leaver form
        if (form.id === 'schoolLeaverForm') {
            const errors = validateSchoolLeaverForm(form);
            if (errors.length > 0) {
                e.preventDefault();
                showFormErrors(errors);
            }
        }
    });
    
    // Auto-hide alerts
    document.addEventListener('DOMContentLoaded', function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            setTimeout(() => {
                if (alert.parentElement) {
                    alert.remove();
                }
            }, 5000);
        });
    });
    
    // Initialize tooltips and popovers
    document.addEventListener('DOMContentLoaded', function() {
        initTooltips();
        initPopovers();
    });
}

// ===========================================
// INITIALIZATION
// ===========================================

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initEventListeners();
    
    // Initialize data tables
    const tables = document.querySelectorAll('.data-table');
    tables.forEach(table => {
        initDataTable(table.id);
    });
    
    console.log('School Leavers System initialized');
});

// Export functions for global use
window.AppUtils = {
    showToast,
    formatNumber,
    formatCurrency,
    formatDate,
    isValidEmail,
    isValidQuarter,
    isValidStatisticCode,
    validateSchoolLeaverForm,
    showFormErrors,
    clearFormErrors,
    showLoading,
    hideLoading,
    togglePasswordVisibility,
    deleteRecord,
    makeRequest
}; 